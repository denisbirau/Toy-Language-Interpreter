package examples;

import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

import java.util.ArrayList;
import java.util.Arrays;

public class Examples {
    private final IStatement example1 = new CompoundStatement(
            new VariableDeclarationStatement("v", new BoolType()),
            new CompoundStatement(
                    new AssignStatement("v", new ValueExpression(new BoolValue(false))),
                    new PrintStatement(new VariableExpression("v"))
            )
    );

    private final IStatement example2 = new CompoundStatement(
            new VariableDeclarationStatement("a", new IntType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("b", new IntType()),
                    new CompoundStatement(
                            new AssignStatement("a",
                                    new ArithmeticExpression('+',
                                            new ValueExpression(new IntValue(2)),
                                            new ArithmeticExpression('*',
                                                    new ValueExpression(new IntValue(3)),
                                                    new ValueExpression(new IntValue(5))
                                            )
                                    )
                            ),
                            new CompoundStatement(
                                    new AssignStatement("b",
                                            new ArithmeticExpression('+',
                                                    new VariableExpression("a"),
                                                    new ValueExpression(new IntValue(1))
                                            )
                                    ),
                                    new PrintStatement(new VariableExpression("b"))
                            )
                    )
            )
    );

    private final IStatement example3 = new CompoundStatement(
            new VariableDeclarationStatement("a", new BoolType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(
                            new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                            new CompoundStatement(
                                    new IfStatement(
                                            new VariableExpression("a"),
                                            new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                            new AssignStatement("v", new ValueExpression(new IntValue(3)))
                                    ),
                                    new PrintStatement(new VariableExpression("v"))
                            )
                    )
            )
    );

    private final IStatement example4 = new CompoundStatement(
            new VariableDeclarationStatement("varf", new StringType()),
            new CompoundStatement(
                    new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                    new CompoundStatement(
                            new OpenFileStatement(new VariableExpression("varf")),
                            new CompoundStatement(
                                    new VariableDeclarationStatement("varc", new IntType()),
                                    new CompoundStatement(
                                            new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("varc")),
                                                    new CompoundStatement(
                                                            new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                            new CompoundStatement(
                                                                    new PrintStatement(new VariableExpression("varc")),
                                                                    new CloseFileStatement(new VariableExpression("varf"))
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    private final IStatement example5 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a", new VariableExpression("v")),
                                    new CompoundStatement(
                                            new PrintStatement(new VariableExpression("v")),
                                            new PrintStatement(new VariableExpression("a"))
                                    )
                            )
                    )
            )
    );

    private final IStatement example6 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a", new VariableExpression("v")),
                                    new CompoundStatement(
                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                            new PrintStatement(new ArithmeticExpression(
                                                    '+',
                                                    new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                    new ValueExpression(new IntValue(5))))
                                    )
                            )
                    )
            )
    );

    private final IStatement example7 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                            new CompoundStatement(
                                    new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                    new PrintStatement(new ArithmeticExpression(
                                            '+',
                                            new ReadHeapExpression(new VariableExpression("v")),
                                            new ValueExpression(new IntValue(5))
                                    ))
                            )
                    )
            )
    );

    private final IStatement example8 = new CompoundStatement(
            new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a", new VariableExpression("v")),
                                    new CompoundStatement(
                                            new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
                                    )
                            )
                    )
            )
    );

    private final IStatement example9 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new AssignStatement("v", new ValueExpression(new IntValue(4))),
                    new CompoundStatement(
                            new WhileStatement(
                                    new RelationalExpression(
                                            ">",
                                            new VariableExpression("v"),
                                            new ValueExpression(new IntValue(0))
                                    ),
                                    new CompoundStatement(
                                            new PrintStatement(new VariableExpression("v")),
                                            new AssignStatement(
                                                    "v",
                                                    new ArithmeticExpression(
                                                            '-',
                                                            new VariableExpression("v"),
                                                            new ValueExpression(new IntValue(1))
                                                    )
                                            )
                                    )
                            ),
                            new PrintStatement(new VariableExpression("v"))
                    )
            )
    );

    private final IStatement example10 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("a", new RefType(new IntType())),
                    new CompoundStatement(
                            new AssignStatement("v", new ValueExpression(new IntValue(10))),
                            new CompoundStatement(
                                    new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                    new CompoundStatement(
                                            new ForkStatement(new CompoundStatement(
                                                    new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                    new CompoundStatement(
                                                            new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                            new CompoundStatement(
                                                                    new PrintStatement(new VariableExpression("v")),
                                                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                            )
                                                    )
                                            )),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("v")),
                                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                            )
                                    )
                            )
                    )
            )
    );

    private final IStatement example11 = new CompoundStatement(
            new VariableDeclarationStatement("v1", new RefType(new IntType())),
            new CompoundStatement(
                    new VariableDeclarationStatement("v2", new RefType(new IntType())),
                    new CompoundStatement(
                            new VariableDeclarationStatement("v3", new RefType(new IntType())),
                            new CompoundStatement(
                                    new VariableDeclarationStatement("cnt", new IntType()),
                                    new CompoundStatement(
                                            new HeapAllocationStatement("v1", new ValueExpression(new IntValue(2))),
                                            new CompoundStatement(
                                                    new HeapAllocationStatement("v2", new ValueExpression(new IntValue(3))),
                                                    new CompoundStatement(
                                                            new HeapAllocationStatement("v3", new ValueExpression(new IntValue(4))),
                                                            new CompoundStatement(
                                                                    new NewLatchStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),
                                                                    new CompoundStatement(
                                                                            new ForkStatement(
                                                                                    new CompoundStatement(
                                                                                            new HeapWritingStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                            new CompoundStatement(
                                                                                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                                    new CompoundStatement(
                                                                                                            new CountDownStatement("cnt"),
                                                                                                            new ForkStatement(
                                                                                                                    new CompoundStatement(
                                                                                                                            new HeapWritingStatement("v2", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                                            new CompoundStatement(
                                                                                                                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
                                                                                                                                    new CompoundStatement(
                                                                                                                                            new CountDownStatement("cnt"),
                                                                                                                                            new ForkStatement(
                                                                                                                                                    new CompoundStatement(
                                                                                                                                                            new HeapWritingStatement("v3", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v3")), new ValueExpression(new IntValue(10)))),
                                                                                                                                                            new CompoundStatement(
                                                                                                                                                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v3"))),
                                                                                                                                                                    new CountDownStatement("cnt")
                                                                                                                                                            )
                                                                                                                                                    )
                                                                                                                                            )
                                                                                                                                    )
                                                                                                                            )
                                                                                                                    )
                                                                                                            )
                                                                                                    )
                                                                                            )
                                                                                    )
                                                                            ),
                                                                            new CompoundStatement(
                                                                                    new AwaitStatement("cnt"),
                                                                                    new CompoundStatement(
                                                                                            new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                            new CompoundStatement(
                                                                                                    new CountDownStatement("cnt"),
                                                                                                    new PrintStatement(new ValueExpression(new IntValue(100)))
                                                                                            )
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    private final IStatement example12 = new CompoundStatement(
            new VariableDeclarationStatement("v1", new RefType(new IntType())),
            new CompoundStatement(
                    new VariableDeclarationStatement("v2", new RefType(new IntType())),
                    new CompoundStatement(
                            new VariableDeclarationStatement("x", new IntType()),
                            new CompoundStatement(
                                    new VariableDeclarationStatement("q", new IntType()),
                                    new CompoundStatement(
                                            new HeapAllocationStatement("v1", new ValueExpression(new IntValue(20))),
                                            new CompoundStatement(
                                                    new HeapAllocationStatement("v2", new ValueExpression(new IntValue(30))),
                                                    new CompoundStatement(
                                                            new NewLockStatement("x"),
                                                            new CompoundStatement(
                                                                    new ForkStatement(new CompoundStatement(
                                                                            new ForkStatement(new CompoundStatement(
                                                                                    new LockStatement("x"),
                                                                                    new CompoundStatement(
                                                                                            new HeapWritingStatement("v1", new ArithmeticExpression('-', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)))),
                                                                                            new UnlockStatement("x")
                                                                                    )
                                                                            )),
                                                                            new CompoundStatement(
                                                                                    new LockStatement("x"),
                                                                                    new CompoundStatement(
                                                                                            new HeapWritingStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                                            new UnlockStatement("x")
                                                                                    )
                                                                            )
                                                                    )),
                                                                    new CompoundStatement(
                                                                            new NewLockStatement("q"),
                                                                            new CompoundStatement(
                                                                                    new ForkStatement(new CompoundStatement(
                                                                                            new ForkStatement(new CompoundStatement(
                                                                                                    new LockStatement("q"),
                                                                                                    new CompoundStatement(
                                                                                                            new HeapWritingStatement("v2", new ArithmeticExpression('+', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(5)))),
                                                                                                            new UnlockStatement("q")
                                                                                                    )
                                                                                            )),
                                                                                            new CompoundStatement(
                                                                                                    new LockStatement("q"),
                                                                                                    new CompoundStatement(
                                                                                                            new HeapWritingStatement("v2", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                                            new UnlockStatement("q")
                                                                                                    )
                                                                                            )
                                                                                    )),
                                                                                    new CompoundStatement(
                                                                                            new NoStatement(),
                                                                                            new CompoundStatement(
                                                                                                    new NoStatement(),
                                                                                                    new CompoundStatement(
                                                                                                            new NoStatement(),
                                                                                                            new CompoundStatement(
                                                                                                                    new NoStatement(),
                                                                                                                    new CompoundStatement(
                                                                                                                            new LockStatement("x"),
                                                                                                                            new CompoundStatement(
                                                                                                                                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                                                                    new CompoundStatement(
                                                                                                                                            new UnlockStatement("x"),
                                                                                                                                            new CompoundStatement(
                                                                                                                                                    new LockStatement("q"),
                                                                                                                                                    new CompoundStatement(
                                                                                                                                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
                                                                                                                                                            new UnlockStatement("q")
                                                                                                                                                    )
                                                                                                                                            )
                                                                                                                                    )
                                                                                                                            )
                                                                                                                    )
                                                                                                            )
                                                                                                    )
                                                                                            )
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    private final IStatement example13 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new AssignStatement("v", new ValueExpression(new IntValue(2))),
                    new CompoundStatement(
                            new VariableDeclarationStatement("w", new IntType()),
                            new CompoundStatement(
                                    new AssignStatement("w", new ValueExpression(new IntValue(5))),
                                    new CompoundStatement(
                                            new CallStatement("sum", Arrays.asList(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))), new VariableExpression("w"))),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("v")),
                                                        new ForkStatement(new CompoundStatement(
                                                                new CallStatement("product", Arrays.asList(new VariableExpression("v"), new VariableExpression("w"))),
                                                                new ForkStatement(new CallStatement("sum", Arrays.asList(new VariableExpression("v"), new VariableExpression("w"))))
                                                        ))
                                            )
                                    )
                            )
                    )
            )
    );

    private final IStatement example14 = new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                    new AssignStatement("v", new ValueExpression(new IntValue(10))),
                    new CompoundStatement(
                            new ForkStatement(new CompoundStatement(
                                    new AssignStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))),
                                    new CompoundStatement(
                                            new AssignStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))),
                                            new PrintStatement(new VariableExpression("v"))
                                    )
                            )),
                            new CompoundStatement(
                                    new SleepStatement(new IntValue(10)),
                                    new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))))
                            )
                    )
            )
    );

    private final IStatement example15 = new CompoundStatement(
            new VariableDeclarationStatement("b", new BoolType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("c", new IntType()),
                    new CompoundStatement(
                            new AssignStatement("b", new ValueExpression(new BoolValue(true))),
                            new CompoundStatement(
                                    new ConditionalAssignStatement("c", new VariableExpression("b"), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                    new CompoundStatement(
                                            new PrintStatement(new VariableExpression("c")),
                                            new CompoundStatement(
                                                    new ConditionalAssignStatement("c", new ValueExpression(new BoolValue(false)), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
                                                    new PrintStatement(new VariableExpression("c"))
                                            )
                                    )
                            )
                    )
            )
    );

    private final IStatement example16 = new CompoundStatement(
            new VariableDeclarationStatement("a", new RefType(new IntType())),
            new CompoundStatement(
                    new HeapAllocationStatement("a", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(
                            new ForStatement("v",
                                    new ValueExpression(new IntValue(0)),
                                    new ValueExpression(new IntValue(3)),
                                    new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))),
                                    new ForkStatement(new CompoundStatement(
                                            new PrintStatement(new VariableExpression("v")),
                                            new AssignStatement("v", new ArithmeticExpression('*', new VariableExpression("v"), new ReadHeapExpression(new VariableExpression("a"))))
                                    ))
                            ),
                            new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                    )
            )
    );

    private final IStatement example17 = new CompoundStatement(
            new VariableDeclarationStatement("a", new IntType()),
            new CompoundStatement(
                    new VariableDeclarationStatement("b", new IntType()),
                    new CompoundStatement(
                            new VariableDeclarationStatement("c", new IntType()),
                            new CompoundStatement(
                                    new AssignStatement("a", new ValueExpression(new IntValue(1))),
                                    new CompoundStatement(
                                            new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                            new CompoundStatement(
                                                    new AssignStatement("c", new ValueExpression(new IntValue(5))),
                                                    new CompoundStatement(
                                                            new SwitchStatement(
                                                                    new ArithmeticExpression('*', new VariableExpression("a"), new ValueExpression(new IntValue(10))),
                                                                    new ArithmeticExpression('*', new VariableExpression("b"), new VariableExpression("c")),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new VariableExpression("a")),
                                                                            new PrintStatement(new VariableExpression("b"))
                                                                    ),
                                                                    new ValueExpression(new IntValue(10)),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                            new PrintStatement(new ValueExpression(new IntValue(200)))
                                                                    ),
                                                                    new PrintStatement(new ValueExpression(new IntValue(300)))
                                                            ),
                                                            new PrintStatement(new ValueExpression(new IntValue(300)))
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    private final IStatement example18 = new CompoundStatement(
            new VariableDeclarationStatement("v1", new RefType(new IntType())),
            new CompoundStatement(
                    new VariableDeclarationStatement("cnt", new IntType()),
                    new CompoundStatement(
                            new HeapAllocationStatement("v1", new ValueExpression(new IntValue(1))),
                            new CompoundStatement(
                                    new CreateSemaphoreStatement("cnt", new ReadHeapExpression(new VariableExpression("v1"))),
                                    new CompoundStatement(
                                            new ForkStatement(
                                                    new CompoundStatement(
                                                            new AcquireStatement("cnt"),
                                                            new CompoundStatement(
                                                                    new HeapWritingStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                    new CompoundStatement(
                                                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                            new ReleaseStatement("cnt")
                                                                    )
                                                            )
                                                    )
                                            ),
                                            new CompoundStatement(
                                                    new ForkStatement(
                                                            new CompoundStatement(
                                                                    new AcquireStatement("cnt"),
                                                                    new CompoundStatement(
                                                                            new HeapWritingStatement("v1", new ArithmeticExpression('*',  new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                            new CompoundStatement(
                                                                                    new HeapWritingStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(2)))),
                                                                                    new CompoundStatement(
                                                                                            new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                            new ReleaseStatement("cnt")
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    ),
                                                    new CompoundStatement(
                                                            new AcquireStatement("cnt"),
                                                            new CompoundStatement(
                                                                    new PrintStatement(new ArithmeticExpression('-', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)))),
                                                                    new ReleaseStatement("cnt")
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    public IStatement[] getExamples() {
        return new IStatement[] { this.example1, this.example2, this.example3, this.example4, this.example5, this.example6, this.example7, this.example8, this.example9, this.example10, this.example11, this.example12, this.example13, this.example14, this.example15, this.example16, this.example17, this.example18 };
    }
}
