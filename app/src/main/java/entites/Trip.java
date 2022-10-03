package entites;

import java.io.Serializable;

public class Trip implements Serializable {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public boolean isRiskAssessment() {
        return RiskAssessment;
    }

    public void setRiskAssessment(boolean riskAssessment) {
        RiskAssessment = riskAssessment;
    }

    @Override
    public String toString() {
        return id + " - " + Name + " - " + Destination + " - " + Date;
    }

    protected String Name;
    protected String Destination;
    protected String Date;
    protected String Description;
    protected int Duration;
    protected boolean RiskAssessment;
}
