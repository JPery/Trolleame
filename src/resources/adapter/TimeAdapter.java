package resources.adapter;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.WebApplicationException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimeAdapter extends XmlAdapter<String, Time> {

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public String marshal(Time v) {
        return timeFormat.format(v);
    }

    @Override
    public Time unmarshal(String v) {
        try {
            return new Time(timeFormat.parse(v).getTime());
        } catch (ParseException e) {
            throw new WebApplicationException();
        }
    }
}