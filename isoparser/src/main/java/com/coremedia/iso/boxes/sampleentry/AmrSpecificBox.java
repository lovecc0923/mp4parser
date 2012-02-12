/*  
 * Copyright 2008 CoreMedia AG, Hamburg
 *
 * Licensed under the Apache License, Version 2.0 (the License); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an AS IS BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package com.coremedia.iso.boxes.sampleentry;


import com.coremedia.iso.*;
import com.coremedia.iso.boxes.AbstractBox;
import com.coremedia.iso.boxes.Box;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/**
 * AMR audio format specific subbox of an audio sample entry.
 *
 * @see com.coremedia.iso.boxes.sampleentry.AudioSampleEntry
 */
public class AmrSpecificBox extends AbstractBox {
    public static final String TYPE = "damr";

    private String vendor;
    private int decoderVersion;
    private int modeSet;
    private int modeChangePeriod;
    private int framesPerSample;

    public AmrSpecificBox() {
        super(TYPE);
    }

    public String getVendor() {
        return vendor;
    }

    public int getDecoderVersion() {
        return decoderVersion;
    }

    public int getModeSet() {
        return modeSet;
    }

    public int getModeChangePeriod() {
        return modeChangePeriod;
    }

    public int getFramesPerSample() {
        return framesPerSample;
    }

    protected long getContentSize() {
        return 9;
    }

    @Override
    public void _parseDetails() {
        byte[] v = new byte[4];
        content.get(v);
        vendor = IsoFile.bytesToFourCC(v);

        decoderVersion = IsoTypeReader.readUInt8(content);
        modeSet = IsoTypeReader.readUInt16(content);
        modeChangePeriod = IsoTypeReader.readUInt8(content);
        framesPerSample = IsoTypeReader.readUInt8(content);

    }


    public void getContent(ByteBuffer bb) throws IOException {
        bb.put(IsoFile.fourCCtoBytes(vendor));
        IsoTypeWriter.writeUInt8(bb, decoderVersion);
        IsoTypeWriter.writeUInt16(bb, modeSet);
        IsoTypeWriter.writeUInt8(bb, modeChangePeriod);
        IsoTypeWriter.writeUInt8(bb, framesPerSample);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("AmrSpecificBox[vendor=").append(getVendor());
        buffer.append(";decoderVersion=").append(getDecoderVersion());
        buffer.append(";modeSet=").append(getModeSet());
        buffer.append(";modeChangePeriod=").append(getModeChangePeriod());
        buffer.append(";framesPerSample=").append(getFramesPerSample());
        buffer.append("]");
        return buffer.toString();
    }
}