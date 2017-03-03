/*
 * Copyright (C) 2017 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.etd;

import java.text.NumberFormat;
import java.time.YearMonth;
import java.util.Locale;

import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.product.SecurityId;
import com.opengamma.strata.product.common.ExchangeId;
import com.opengamma.strata.product.common.PutCall;

/**
 * A utility for generating ETD identifiers.
 * <p>
 * An exchange traded derivative (ETD) is uniquely identified by a set of fields.
 * In most cases, these fields should be kept separate, as on {@link EtdContractSpec}.
 * However, it can be useful to create a single identifier from the separate fields.
 * We do not recommend parsing the combined identifier to retrieve individual fields.
 */
public final class EtdIdUtils {

  /**
   * Scheme used for ETDs.
   */
  public static final String ETD_SCHEME = "OG-ETD";
  /**
   * Prefix for futures.
   */
  private static final String FUT_PREFIX = "F-";
  /**
   * Prefix for option.
   */
  private static final String OPT_PREFIX = "O-";

  //-------------------------------------------------------------------------
  /**
   * Creates an identifier for a contract specification.
   * <p>
   * This will have the format:
   * {@code 'OG-Etd~F-ECAG-FGBS'} or {@code 'OG-Etd~O-ECAG-OGBS'}.
   *
   * @param type  type of the contract - future or option
   * @param exchangeId  the MIC code of the exchange where the instruments are traded
   * @param contractCode  the code supplied by the exchange for use in clearing and margining, such as in SPAN
   * @return the identifier
   */
  public static EtdContractSpecId contractSpecId(EtdType type, ExchangeId exchangeId, String contractCode) {
    ArgChecker.notNull(type, "type");
    ArgChecker.notNull(exchangeId, "exchangeId");
    ArgChecker.notEmpty(contractCode, "contractCode");
    switch (type) {
      case FUTURE:
        return EtdContractSpecId.of(ETD_SCHEME, FUT_PREFIX + exchangeId + "-" + contractCode);
      case OPTION:
        return EtdContractSpecId.of(ETD_SCHEME, OPT_PREFIX + exchangeId + "-" + contractCode);
      default:
        throw new IllegalArgumentException("Unknown ETD type: " + type);
    }
  }

  /**
   * Creates an identifier for an ETD future instrument.
   * <p>
   * This will have the format:
   * {@code 'OG-Etd~F-ECAG-FGBS-201706'}.
   *
   * @param exchangeId  the MIC code of the exchange where the instruments are traded
   * @param contractCode  the code supplied by the exchange for use in clearing and margining, such as in SPAN
   * @param expiryMonth  the month of expiry
   * @param expiryDateCode  the date code of expiry
   * @return the identifier
   */
  public static SecurityId futureId(
      ExchangeId exchangeId,
      String contractCode,
      YearMonth expiryMonth,
      String expiryDateCode) {

    ArgChecker.notNull(exchangeId, "exchangeId");
    ArgChecker.notEmpty(contractCode, "contractCode");
    ArgChecker.isTrue(expiryMonth.getYear() >= 1000 && expiryMonth.getYear() <= 9999, "Invalid expiry year: ", expiryMonth);
    ArgChecker.isTrue(expiryDateCode.length() <= 2, "Invalid expiry date code: ", expiryDateCode);

    String id = FUT_PREFIX +
        exchangeId + "-" +
        contractCode + "-" +
        expiryMonth.getYear() +
        ((char) ((expiryMonth.getMonthValue() / 10) + '0')) +
        ((char) ((expiryMonth.getMonthValue() % 10) + '0')) +
        expiryDateCode;
    return SecurityId.of(ETD_SCHEME, id);
  }

  /**
   * Creates an identifier for an ETD future instrument.
   * <p>
   * This will have the format:
   * {@code 'OG-Etd~O-ECAG-OGBS-201706-P1.50'}.
   *
   * @param exchangeId  the MIC code of the exchange where the instruments are traded
   * @param contractCode  the code supplied by the exchange for use in clearing and margining, such as in SPAN
   * @param expiryMonth  the month of expiry
   * @param expiryDateCode  the date code of expiry
   * @param putCall  the Put/Call flag
   * @param strikePrice  the strike price
   * @return the identifier
   */
  public static SecurityId optionId(
      ExchangeId exchangeId,
      String contractCode,
      YearMonth expiryMonth,
      String expiryDateCode,
      PutCall putCall,
      double strikePrice) {

    ArgChecker.notNull(exchangeId, "exchangeId");
    ArgChecker.notEmpty(contractCode, "contractCode");
    ArgChecker.isTrue(expiryMonth.getYear() >= 1000 && expiryMonth.getYear() <= 9999, "Invalid expiry year: ", expiryMonth);
    ArgChecker.isTrue(expiryDateCode.length() <= 2, "Invalid expiry date code: ", expiryDateCode);

    String putCallStr = putCall == PutCall.PUT ? "P" : "C";
    String expiryDateCodeStr = expiryDateCode;
    if (expiryDateCodeStr.length() == 1 && expiryDateCodeStr.charAt(0) >= '0' && expiryDateCodeStr.charAt(0) <= '9') {
      expiryDateCodeStr = '0' + expiryDateCodeStr;
    }
    NumberFormat f = NumberFormat.getIntegerInstance(Locale.ENGLISH);
    f.setGroupingUsed(false);
    f.setMaximumFractionDigits(6);
    String id = OPT_PREFIX +
        exchangeId + "-" +
        contractCode + "-" +
        expiryMonth.getYear() +
        ((char) ((expiryMonth.getMonthValue() / 10) + '0')) +
        ((char) ((expiryMonth.getMonthValue() % 10) + '0')) +
        expiryDateCodeStr + "-" +
        putCallStr +
        f.format(strikePrice);
    return SecurityId.of(ETD_SCHEME, id);
  }

  //-------------------------------------------------------------------------
  // restricted constructor
  private EtdIdUtils() {
  }

}
