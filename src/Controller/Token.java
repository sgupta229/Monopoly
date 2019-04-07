package Controller;

public class Token {
    int location;
    String name;

    public Token(int location, String name) {
        this.location = location;
        this.name = name;
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

}
