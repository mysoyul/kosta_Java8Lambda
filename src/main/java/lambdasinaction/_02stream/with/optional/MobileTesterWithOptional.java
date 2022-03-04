package lambdasinaction._02stream.with.optional;

import java.util.Optional;

public class MobileTesterWithOptional {
	
	public static void main(String[] args) {
		ScreenResolution resolution = new ScreenResolution(750,1334);
		//Null을 허용하지 않는 Optional.of()
		DisplayFeatures dfeatures = new DisplayFeatures("4.7", Optional.of(resolution));
		Mobile mobile = new Mobile(2015001, "Apple", "iPhone 6s", Optional.of(dfeatures));
		
		MobileService mService = new MobileService();
		
		int width = mService.getMobileScreenWidth(Optional.of(mobile));
		System.out.println("Apple iPhone 6s Screen Width = " + width);

		//Not-Null인 경우 Optional의 ifPresent(), isPresent() 사용
		Optional<ScreenResolution> resolutionOptional = dfeatures.getResolution();
		resolutionOptional.ifPresent(screenResolution -> System.out.println(screenResolution.getHeight()));
		System.out.println("isPresent = " + resolutionOptional.isPresent());

		//Null을 허용하는 Optional.empty()
		Mobile mobile2 = new Mobile(2015001, "Apple", "iPhone 6s", Optional.empty());		
		int width2 = mService.getMobileScreenWidth(Optional.of(mobile2));
		System.out.println("Apple iPhone 16s Screen Width = " + width2);

		//Null 인 경우 Optional의 ifPresent(), isPresent(),orElseGet 사용
		Optional<DisplayFeatures> featuresOptional = mobile2.getDisplayFeatures();
		featuresOptional.ifPresent(System.out::println);
		System.out.println("isPresent = " + featuresOptional.isPresent());

		DisplayFeatures features =
				featuresOptional.orElseGet(() -> new DisplayFeatures("0", Optional.of(new ScreenResolution(10, 10))));
		System.out.println("DisplayFeatures SIZE " + features.getResolution().get().getHeight());

		features = featuresOptional.orElseThrow(() -> new RuntimeException("DisplayFeatures Not Found"));
	}

}
