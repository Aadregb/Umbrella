package Utilities;

import java.io.InvalidClassException;

public class IncorrectCharNIF extends InvalidClassException {
    public IncorrectCharNIF(String reason) {
        super(reason);
    }
}
