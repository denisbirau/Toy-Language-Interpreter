package controller;

import exceptions.ADTException;
import exceptions.MyException;
import javafx.util.Pair;
import model.ProgramState;
import model.adts.*;
import model.statements.IStatement;
import model.values.IValue;
import model.values.RefValue;
import model.values.StringValue;
import repository.IRepository;

import java.io.BufferedReader;
import java.net.ContentHandler;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private final IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public List<ProgramState> getAllProgramStates() {
        return this.repository.getProgramStateList();
    }

    // Garbage collector -----------------------------------------------------------------------------------------------
    private List<Integer> getSymbolTableAndHeapAddresses(Collection<IValue> symbolTableValues, Collection<IValue> heapValues) {
        return Stream.concat(
                symbolTableValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> ((RefValue) v).getAddress()),
                heapValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> ((RefValue) v).getAddress())
        ).collect(Collectors.toList());
    }

    private Map<Integer, IValue> safeGarbageCollector(List<Integer> addresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void conservativeGarbageCollector(List<ProgramState> programStates) {
        List<Integer> addresses = Objects.requireNonNull(
                programStates.stream()
                .map(programState -> getSymbolTableAndHeapAddresses(programState.getSymbolTable().values(),
                        programState.getHeap().getMap().values()))
                .map(Collection::stream)
                .reduce(Stream::concat).orElse(null)).toList();
        programStates.forEach(programState -> programState.getHeap()
                .setHeap(safeGarbageCollector(addresses, programStates.getFirst().getHeap().getMap())));
    }
    // -----------------------------------------------------------------------------------------------------------------

    public void executeOneStepForAllPrograms(List<ProgramState> programStates) {
        programStates.forEach(programState -> {
            try {
                this.repository.logProgramStateExecution(programState);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        List<Callable<ProgramState>> callables = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::executeOneStep)).toList();

        try {
            List<ProgramState> newProgramStates = this.executor.invokeAll(callables).stream()
                    .map(programStateFuture -> {
                        try {
                            return programStateFuture.get();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull).toList();
            programStates.addAll(newProgramStates);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        programStates.forEach(programState -> {
            try {
                this.repository.logProgramStateExecution(programState);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        this.repository.setProgramStateList(programStates);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> list) {
        return list.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }

    public void typeCheck() throws MyException {
        this.repository.getProgramStateList().getFirst().typeCheck();
    }

    public void executeOneStepForAllProgramsGUI() {
        this.executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrograms(this.repository.getProgramStateList());
        this.repository.setProgramStateList(programStates);
        if (programStates.isEmpty())
            throw new MyException("Program is done!");
        conservativeGarbageCollector(programStates);
        this.executeOneStepForAllPrograms(programStates);
        this.executor.shutdownNow();
    }

    public MyIHeap<IValue> getHeapTable() {
        return this.repository.getProgramStateList().getFirst().getHeap();
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.repository.getProgramStateList().getFirst().getFileTable();
    }

    public MyIList<IValue> getOutput() {
        return this.repository.getProgramStateList().getFirst().getOutput();
    }

    public MyICountDownLatchTable<Integer> getLatchTable() {
        return this.repository.getProgramStateList().getFirst().getLatchTable();
    }

    public MyILockTable<Integer> getLockTable() {
        return this.repository.getProgramStateList().getFirst().getLockTable();
    }

    public MyIProcedureTable<String, Pair<List<String>, IStatement>> getProcedureTable() {
        return this.repository.getProgramStateList().getFirst().getProcedureTable();
    }

    public MyISemaphoreTable<Pair<Integer, List<Integer>>> getSemaphoreTable() {
        return this.repository.getProgramStateList().getFirst().getSemaphoreTable();
    }
}
