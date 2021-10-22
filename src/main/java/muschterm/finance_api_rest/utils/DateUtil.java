package muschterm.finance_api_rest.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class DateUtil {

	// The zone ID should correlate to where the purchase was made to technically be accurate.
	//
	// But this could also be misleading in finance since transactions occur in order; so if you make a
	// transaction in Tokyo, then fly to New York in 2 hrs (somehow), and make a transaction, that New York transaction
	// will technically occur before the Tokyo one; which is incorrect. This is why financial institutions usually
	// stick to a specific timezone.  This date should be stored as if UTC, but displayed in the current timezone
	// instead.
	//
	// As part of Ofx though, the as-of-date, posted-date, etc, are all stored as UTC - 8 hrs to ensure a true "static"
	// date that works anywhere in the world.
	public static LocalDate handleStaticDate(Date staticDate) {
		return LocalDate.ofInstant(staticDate.toInstant(), ZoneId.systemDefault());
	}

}
