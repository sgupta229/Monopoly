package Controller;

import Model.Space;

import java.security.InvalidParameterException;

public interface Board {
    public Space getSpaceAt(int location) throws IndexOutOfBoundsException;
    public int getLocationOfSpace(Space space) throws InvalidParameterException;
}
