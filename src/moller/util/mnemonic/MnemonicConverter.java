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
package moller.util.mnemonic;

import java.awt.event.KeyEvent;

public class MnemonicConverter {

	public static int convertAtoZCharToKeyEvent(char c) {

		c = Character.toLowerCase(c);

		switch (c) {
		case 'a':
			return KeyEvent.VK_A;
		case 'b':
			return KeyEvent.VK_B;
		case 'c':
			return KeyEvent.VK_C;
		case 'd':
			return KeyEvent.VK_D;
		case 'e':
			return KeyEvent.VK_E;
		case 'f':
			return KeyEvent.VK_F;
		case 'g':
			return KeyEvent.VK_G;
		case 'h':
			return KeyEvent.VK_H;
		case 'i':
			return KeyEvent.VK_I;
		case 'j':
			return KeyEvent.VK_J;
		case 'k':
			return KeyEvent.VK_K;
		case 'l':
			return KeyEvent.VK_L;
		case 'm':
			return KeyEvent.VK_M;
		case 'n':
			return KeyEvent.VK_N;
		case 'o':
			return KeyEvent.VK_O;
		case 'p':
			return KeyEvent.VK_P;
		case 'q':
			return KeyEvent.VK_Q;
		case 'r':
			return KeyEvent.VK_R;
		case 's':
			return KeyEvent.VK_S;
		case 't':
			return KeyEvent.VK_T;
		case 'u':
			return KeyEvent.VK_U;
		case 'v':
			return KeyEvent.VK_V;
		case 'w':
			return KeyEvent.VK_W;
		case 'x':
			return KeyEvent.VK_X;
		case 'y':
			return KeyEvent.VK_Y;
		case 'z':
			return KeyEvent.VK_Z;
		default:
			throw new RuntimeException();
		}
	}
}
