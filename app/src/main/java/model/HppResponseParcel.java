package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.realexpayments.hpp.HPPResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created by artursynowiec on 09/03/16.
 */
public class HppResponseParcel implements Parcelable {

    private HPPResponse response;
    protected HppResponseParcel(Parcel in) {
    }

    public static final Creator<HppResponseParcel> CREATOR = new Creator<HppResponseParcel>() {
        @Override
        public HppResponseParcel createFromParcel(Parcel in) {
            return new HppResponseParcel(in);
        }

        @Override
        public HppResponseParcel[] newArray(int size) {
            return new HppResponseParcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(toByteArray(response));

    }

    private byte[] toByteArray(HPPResponse res){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(res);
            byte[] yourBytes = bos.toByteArray();
            return yourBytes;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }

        }

        return null;

    }
}
