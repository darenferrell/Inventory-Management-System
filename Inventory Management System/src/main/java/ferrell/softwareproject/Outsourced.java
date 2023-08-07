package ferrell.softwareproject;


public class Outsourced extends Part {
    String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /** The setter for the text field 'companyName'.
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** The getter for the text field 'companyName'.
     *
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    /**The setter for the text field 'newPartId'.
     * @param newPartId
     */
    public void setID(int newPartId) {
    }
}
