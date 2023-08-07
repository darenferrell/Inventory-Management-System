package ferrell.softwareproject;


public class InHouse extends Part {
    int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * The setter for the text field 'machineId'.
     *
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * The getter for the text field 'machineId'.
     *
     * @return
     */
    public int getMachineId() {
        return machineId;
    }

    public String getCompanyName() {
        return null;
    }
}
