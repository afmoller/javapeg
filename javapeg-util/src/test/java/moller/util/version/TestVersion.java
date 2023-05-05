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
package moller.util.version;


public class TestVersion {

//	@Test
//	public void testGetLatestVersion() {
//		try {
//			Long latestVersion = UpdateChecker.getLatestVersion(new URL("http://javapeg.sourceforge.net/PAGES/updates/version.php"), 5);
//			System.out.println(latestVersion);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testGetVersionInformation() {
//
//		Map<Long, VersionInformation> versions;
//		VersionInformation vi;
//
//		try {
//			versions = UpdateChecker.getVersionInformationMap(new URL("http://javapeg.sourceforge.net/PAGES/updates/versionInformation.php"), 5);
//
//			vi = versions.get(UpdateChecker.getLatestVersion(new URL("http://javapeg.sourceforge.net/PAGES/updates/version.php"), 5));
//
//			System.out.println(vi.getDownnloadURL());
//			System.out.println(vi.getVersionNumber());
//
//			Map<Long, ChangeLog> changeLogs = vi.getChangeLogs();
//
//			Set<Long> keys = changeLogs.keySet();
//
//			for (Long key : keys) {
//				System.out.println(key);
//				System.out.println(changeLogs.get(key).getChangeLog());
//			}
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		}
//	}
}