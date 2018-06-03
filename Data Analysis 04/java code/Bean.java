/**
 * Created by fred on 2018/6/3.
 */
public class Bean {
    private String movie;
    private double rate;
    private String timeStamp;
    private String uid;

    public Bean() {
    }

    public Bean(String movie, double rate, String timeStamp, String uid) {
        this.movie = movie;
        this.rate = rate;
        this.timeStamp = timeStamp;
        this.uid = uid;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "movie='" + movie + '\'' +
                ", rate=" + rate +
                ", timeStamp='" + timeStamp + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
