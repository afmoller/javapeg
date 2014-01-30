/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.util.datatype;

public class Rational {
	private int numerator;
	private int denominator;

	public static final String DELIMITER = "/";

	public Rational(String numerator, String denominator) {
		this(Integer.parseInt(numerator), Integer.parseInt(denominator));
	}

	public Rational(int numerator, int denominator) {
		if (denominator == 0) {
			throw new RuntimeException("Denominator is zero");
		}

		int g = gcd(numerator, denominator);
		this.numerator = numerator / g;
		this.denominator = denominator / g;
	}

	public int getNumerator() {
		return numerator;
	}

	public int getDenominator() {
		return denominator;
	}

	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}

	public String getDelimiter() {
		return DELIMITER;
	}

	@Override
    public String toString() {
		if (denominator == 1) {
			return numerator + "";
		} else {
			return numerator + DELIMITER + denominator;
		}
	}

	private int gcd(int m, int n) {
		if (0 == n) {
			return m;
		} else {
			return gcd(n, m % n);
		}
	}
}
