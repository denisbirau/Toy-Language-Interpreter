package repository;

import exceptions.FileException;
import exceptions.MyException;
import model.ProgramState;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository implements IRepository{
    private List<ProgramState> programStates;
    private final String logFilePath;

    public Repository(String filePath) {
        this.programStates = new LinkedList<>();
        this.logFilePath = filePath;
    }

    @Override
    public void addProgram(ProgramState programState) {
        this.programStates.add(programState);
    }

    @Override
    public void logProgramStateExecution(ProgramState state) throws MyException {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
        logFile.println(state.toString());
        logFile.close();
    }

    @Override
    public List<ProgramState> getProgramStateList() {
        return this.programStates;
    }

    @Override
    public void setProgramStateList(List<ProgramState> newList) {
        this.programStates = newList;
    }

    @Override
    public int size() {
        return this.programStates.size();
    }

    @Override
    public ProgramState getProgramState(int id) {
        for (ProgramState state: this.programStates)
            if (state.getId() == id)
                return state;
        return null;
    }
}
