package com.sq.sohel.blooddonor.utils.JGoogleAuthenticator;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.api.client.util.DateTime;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Longs;
import com.sq.sohel.blooddonor.utils.CommonUtils;

//import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.lang.String;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class TwoFactorAuthenticator {

    public static Date _epoch;
    public int DefaultClockDriftTolerance; //In Minutes
    public boolean UseManagedSha1Algorithm;
    public boolean TryUnmanagedAlgorithmOnFailure;

    private long timeStepSizeInMillis = TimeUnit.SECONDS.toMillis(30);
    private int windowSize = 3;
    private int codeDigits = 6;
    private int numberOfScratchCodes = 5;
    private int keyModulus = (int) Math.pow(10, codeDigits);
    private KeyRepresentation keyRepresentation = KeyRepresentation.BASE32;
    private HmacHashFunction hmacHashFunction = HmacHashFunction.HmacSHA1;

    public TwoFactorAuthenticator() {
        this(true, true);
    }

    public TwoFactorAuthenticator(boolean useManagedSha1, boolean useUnmanagedOnFail) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.set(1970, 1, 1, 0, 0, 0);
        _epoch = cal.getTime();


        DefaultClockDriftTolerance = 5;// TimeSpan.FromMinutes(5);
        UseManagedSha1Algorithm = useManagedSha1;
        TryUnmanagedAlgorithmOnFailure = useUnmanagedOnFail;
    }

    public SetupCode GenerateSetupCode(String accountTitleNoSpaces, String accountSecretKey, int qrCodeWidth, int qrCodeHeight) {
        return GenerateSetupCode(null, accountTitleNoSpaces, accountSecretKey, qrCodeWidth, qrCodeHeight);
    }

    public SetupCode GenerateSetupCode(String issuer, String accountTitleNoSpaces, String accountSecretKey, int qrCodeWidth, int qrCodeHeight) {
        return GenerateSetupCode(issuer, accountTitleNoSpaces, accountSecretKey, qrCodeWidth, qrCodeHeight, false);
    }

    public SetupCode GenerateSetupCode(String issuer, String accountTitleNoSpaces, String accountSecretKey, int qrCodeWidth, int qrCodeHeight, boolean useHttps) {
        if (accountTitleNoSpaces == null) {
            try {
                throw new Exception("Account Title is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        accountTitleNoSpaces = accountTitleNoSpaces.replace(" ", "");

        SetupCode sC = new SetupCode();
        sC.Account = accountTitleNoSpaces;
        sC.AccountSecretKey = accountSecretKey;

        String encodedSecretKey = EncodeAccountSecretKey(accountSecretKey);
        sC.ManualEntryKey = encodedSecretKey;

        String provisionUrl = null;

        if (issuer == null || issuer.isEmpty()) {
            provisionUrl = UrlEncode(java.lang.String.format("otpauth://totp/{0}?secret={1}", accountTitleNoSpaces, encodedSecretKey));
        } else {
            provisionUrl = UrlEncode(java.lang.String.format("otpauth://totp/{0}?secret={1}&issuer={2}", accountTitleNoSpaces, encodedSecretKey, UrlEncode(issuer)));
        }

        String protocol = useHttps ? "https" : "http";
        String url = java.lang.String.format("{0}://chart.googleapis.com/chart?cht=qr&chs={1}x{2}&chl={3}", protocol, qrCodeWidth, qrCodeHeight, provisionUrl);

        sC.QrCodeSetupImageUrl = url;

        return sC;
    }

    private String UrlEncode(String value) {
        StringBuilder result = new StringBuilder();
        char[] validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~".toCharArray();


        for (char symbol : value.toCharArray()) {
            if (java.util.Arrays.asList(validChars).indexOf(symbol) != -1) {
                result.append(symbol);
            } else {
                result.append('%' + java.lang.String.format("{0:X2}", (int) symbol));
            }
        }

        return result.toString().replace(" ", "%20");
    }

    private String EncodeAccountSecretKey(String accountSecretKey) {
        //if (accountSecretKey.Length < 10)
        //{
        //    accountSecretKey = accountSecretKey.PadRight(10, '0');
        //}

        //if (accountSecretKey.Length > 12)
        //{
        //    accountSecretKey = accountSecretKey.SubString(0, 12);
        //}
        //Charset.availableCharsets().
        //return Base32Encode(Encoding.UTF8.GetBytes(accountSecretKey));
        try {
            return Base32Encode(accountSecretKey.getBytes("UTF8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private String Base32Encode(byte[] data) {
        int inByteSize = 8;
        int outByteSize = 5;
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();

        int i = 0, index = 0, digit = 0;
        int current_byte, next_byte;
        StringBuilder result = new StringBuilder((data.length + 7) * inByteSize / outByteSize);

        while (i < data.length) {
            current_byte = (data[i] >= 0) ? data[i] : (data[i] + 256); // Unsign

            /* Is the current digit going to span a byte boundary? */
            if (index > (inByteSize - outByteSize)) {
                if ((i + 1) < data.length)
                    next_byte = (data[i + 1] >= 0) ? data[i + 1] : (data[i + 1] + 256);
                else
                    next_byte = 0;

                digit = current_byte & (0xFF >> index);
                index = (index + outByteSize) % inByteSize;
                digit <<= index;
                digit |= next_byte >> (inByteSize - index);
                i++;
            } else {
                digit = (current_byte >> (inByteSize - (index + outByteSize))) & 0x1F;
                index = (index + outByteSize) % inByteSize;
                if (index == 0)
                    i++;
            }
            result.append(alphabet[digit]);
        }

        return result.toString();
    }

    public String GeneratePINAtInterval(String accountSecretKey, long counter, int digits) {
        if (digits <= 0) {
            digits = 6;
        }
        return GenerateHashedCode(accountSecretKey, counter, digits);
    }

    private String GenerateHashedCode(String secret, long iterationNumber, int digits) {
        if (digits <= 0) {
            digits = 6;
        }
        try {
            byte[] key = secret.getBytes("UTF8");
            return GenerateHashedCode(key, iterationNumber, digits);
        } catch (UnsupportedEncodingException e) {
            return null;
        }


    }

    public byte[] long2byte(long l) {

        byte[] dss = BitConverter.getBytes(l);

        ArrayList<Byte> bytes = new ArrayList<Byte>();
        while (l != 0) {
            bytes.add((byte) (l % (0xff + 1)));
            l = l >> 8;
        }
        byte[] bytesp = new byte[bytes.size()];
        for (int i = bytes.size() - 1, j = 0; i >= 0; i--, j++) {
            bytesp[j] = bytes.get(i);
        }
        return bytesp;


    }

    private String GenerateHashedCode(byte[] key, long iterationNumber, int digits) {
        if (digits <= 0) {
            digits = 6;
        }

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(iterationNumber);
        byte[] counter1 = buffer.array();

        counter1 = long2byte(iterationNumber);

        byte[] counter = Longs.toByteArray(iterationNumber);//BitConverter.GetBytes(iterationNumber);

        if (ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN)) {
            Collections.reverse(Arrays.asList(counter));
        } else {

        }
        //HMACSHA1 hmac = getHMACSha1Algorithm(key);

        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = null; //HmacSHA1
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(signKey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        // Initializing the MAC algorithm.

        byte[] hash = mac.doFinal(counter);
        //byte[] hash = Hashing.sha1().hashBytes(key).asBytes();// getHMACSha1Algorithm(key);// hmac.ComputeHash(counter);

        int offset = hash[hash.length - 1] & 0xF;

        // Convert the 4 bytes into an integer, ignoring the sign.
        int binary =
                ((hash[offset] & 0x7f) << 24)
                        | (hash[offset + 1] << 16)
                        | (hash[offset + 2] << 8)
                        | (hash[offset + 3]);

        int password = binary % (int) Math.pow(10, digits);
        //return password.toString(new String('0', digits));
        //return StringUtils.leftPad(String.valueOf(password), digits, '0');
        return String.valueOf(password);

    }

    private long GetCurrentCounter() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        final String utcTime = sdf.format(new Date());

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTime(new Date());
        return GetCurrentCounter(cal.getTime(), _epoch, 30);
    }

    private long GetCurrentCounter(Date now, Date epoch, int timeStep) {
        return (long) ((now.getTime() - epoch.getTime()) / 1000) / timeStep;
    }

    private byte[] getHMACSha1Algorithm(byte[] key) {


        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(key);
            return crypt.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1_ALGORITHM);
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(signingKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return mac.doFinal();
    }

    public boolean ValidateTwoFactorPIN(String accountSecretKey, String twoFactorCodeFromClient) {
        return ValidateTwoFactorPIN(accountSecretKey, twoFactorCodeFromClient, DefaultClockDriftTolerance);
    }

    public boolean ValidateTwoFactorPIN(String accountSecretKey, String twoFactorCodeFromClient, int timeTolerance) {
        String[] codes = GetCurrentPINs(accountSecretKey, timeTolerance);
        return CommonUtils.contains2(codes, twoFactorCodeFromClient);//codes.Any(c => c == twoFactorCodeFromClient);
    }

    public String GetCurrentPIN(String accountSecretKey) {
        return GeneratePINAtInterval(accountSecretKey, GetCurrentCounter(), 6);
    }

    public String GetCurrentPIN(String accountSecretKey, Date now) {
        return GeneratePINAtInterval(accountSecretKey, GetCurrentCounter(now, _epoch, 30), 6);
    }

    public String[] GetCurrentPINs(String accountSecretKey) {
        return GetCurrentPINs(accountSecretKey, DefaultClockDriftTolerance);
    }

    public String[] GetCurrentPINs(String accountSecretKey, int timeTolerance) {
        List<String> codes = new ArrayList<>();
        long iterationCounter = GetCurrentCounter();
        int iterationOffset = 0;

        if (timeTolerance * 60 > 30) {
            iterationOffset = (int) (timeTolerance * 60 / 30.00);
        }

        long iterationStart = iterationCounter - iterationOffset;
        long iterationEnd = iterationCounter + iterationOffset;

        for (long counter = iterationStart; counter <= iterationEnd; counter++) {
            codes.add(GeneratePINAtInterval(accountSecretKey, counter, 6));
        }

        return codes.toArray(new String[codes.size()]);
    }
}
