package model;

import java.util.Date;

public class Deadline {
    
    Deadline(Date pDate)
    {
        mDate=pDate;
    }

    public Deadline(int i) {
		mDate = new Date();
		mDate.setTime(System.currentTimeMillis()+i);
	}

	public long TimeUntil()
    {
        return mDate.getTime()-(new Date()).getTime();
    }
        
    private Date mDate;
}
