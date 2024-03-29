package com.coremedia.iso.boxes.apple;

import com.coremedia.iso.BoxFactory;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoInputStream;
import com.coremedia.iso.IsoOutputStream;
import com.coremedia.iso.Utf8;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.FullBox;

import java.io.IOException;

/**
 * Apple Meaning box. Allowed as subbox of "----" box.
 *
 * @see com.coremedia.iso.boxes.apple.AppleGenericBox
 */
public final class AppleMeanBox extends FullBox {
  public static final String TYPE = "mean";
  private String meaning;

  public AppleMeanBox() {
    super(IsoFile.fourCCtoBytes(TYPE));
  }

  protected long getContentSize() {
    return Utf8.convert(meaning).length;
  }

  public String getDisplayName() {
    return "Apple Meaning Box";
  }

  protected void getContent(IsoOutputStream os) throws IOException {
    os.writeStringNoTerm(meaning);
  }

  public String getMeaning() {
    return meaning;
  }

  public void setMeaning(String meaning) {
    this.meaning = meaning;
  }

  @Override
  public void parse(IsoInputStream in, long size, BoxFactory boxFactory, Box lastMovieFragmentBox) throws IOException {
    super.parse(in, size, boxFactory, lastMovieFragmentBox);
    setMeaning(in.readString((int) (size - 4)));
  }
}
