package Controller;

public class Token {
    int location;

    public Token(int location) {
        this.location = location;
    }

    //ADD FUNCTIONALITY OF WRAPPING AROUND BOARD ONCE BOARD CLASS IS MADE
    public int move(int numSpaces) {
        location += numSpaces;
        return location;
    }

    public int moveTo(int newSpace) {
        location = newSpace;
        return newSpace;
    }

    public int getCurrentLocation() {
        return location;
    }

    public void setLocation(int newLocation) {
        location = newLocation;
    }

}
