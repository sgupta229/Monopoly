package Controller;

import Model.Space;

public interface Board {
    public Space getSpaceAt(int location);
    public int getLocationOfSpace(Space space);
}
