package entites;

public class Expenses {
    public int id;
    public String expensesType;
    public int expensesAmount;
    public String expensesDate;
    public String additionalComments;
    public int tripId;
    public String tripName;

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(String expensesType) {
        this.expensesType = expensesType;
    }

    public int getExpensesAmount() {
        return expensesAmount;
    }

    public void setExpensesAmount(int expensesAmount) {
        this.expensesAmount = expensesAmount;
    }

    public String getExpensesDate() {
        return expensesDate;
    }

    public void setExpensesDate(String expensesDate) {
        this.expensesDate = expensesDate;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    @Override
    public String toString() {
        return id + " - " + expensesType + " - " + expensesDate;
    }
}
