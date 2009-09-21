package moller.util.test.unittest.version;


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