/*
 * This Application and all Its resources are the property of
 * John Mtonga(Application Developer)
 */
package ztl.math.obj;

/**
 *
 * @author user
 */
public enum RequestType {

    INITIATE(1), CONTINUE(2), TERMINATE(3);

    private final int ID;

    private RequestType(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public static RequestType getRequestType(int ID) {
        for (RequestType r : RequestType.values()) {
            if (r.getID() == ID) {
                return r;
            }
        }
        return null;
    }

}
