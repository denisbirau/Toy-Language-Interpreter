package repository;

import exceptions.MyException;
import model.ProgramState;
import java.util.List;

public interface IRepository {
    void addProgram(ProgramState programState);
    void logProgramStateExecution(ProgramState state) throws MyException;

    List<ProgramState> getProgramStateList();
    void setProgramStateList(List<ProgramState> newList);
    int size();

    ProgramState getProgramState(int id);
}
