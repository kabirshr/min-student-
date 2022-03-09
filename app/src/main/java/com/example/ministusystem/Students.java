package com.example.ministusystem;

import android.os.Parcel;
import android.os.Parcelable;

public class Students implements Parcelable {

    protected Students(Parcel in) {
        mAge = in.readInt();
        mCurr = in.readInt();
        mName = in.readString();
        mathsC = in.readByte() != 0;
        englishC = in.readByte() != 0;
        computerC = in.readByte() != 0;
        musicC = in.readByte() != 0;
        paintingC = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mAge);
        dest.writeInt(mCurr);
        dest.writeString(mName);
        dest.writeByte((byte) (mathsC ? 1 : 0));
        dest.writeByte((byte) (englishC ? 1 : 0));
        dest.writeByte((byte) (computerC ? 1 : 0));
        dest.writeByte((byte) (musicC ? 1 : 0));
        dest.writeByte((byte) (paintingC ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Students> CREATOR = new Creator<Students>() {
        @Override
        public Students createFromParcel(Parcel in) {
            return new Students(in);
        }

        @Override
        public Students[] newArray(int size) {
            return new Students[size];
        }
    };

    public int getAge() {
        return mAge;
    }
    public String getName() {
        return mName;
    }
    public int getCurr() {
        return mCurr;
    }

    public int mAge, mCurr;
    public String mName;
    public boolean mathsC,englishC, computerC, musicC, paintingC;



    public boolean isMathsC() {
        return mathsC;
    }
    public boolean isEnglishC() {
        return englishC;
    }
    public boolean isComputerC() {
        return computerC;
    }
    public boolean isMusicC() {
        return musicC;
    }
    public boolean isPaintingC() {
        return paintingC;
    }

    public Students(int age, String name){
        mAge = age;
        mName = name;
    }

    public void save_curriculums(boolean mathsc, boolean englishc, boolean computerc, boolean musicc, boolean paintingc){
        mathsC = mathsc;
        englishC = englishc;
        computerC = computerc;
        musicC = musicc;
        paintingC = paintingc;
    }
    public void setCc(int curriculum){this.mCurr = curriculum; }

}

