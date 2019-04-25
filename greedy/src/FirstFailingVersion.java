public class FirstFailingVersion {

    public static long firstBadVersion(long n, IsFailingVersion isBadVersion) {
        long start = n / 2;
        long end = n;
        boolean foundFirst = false;
        boolean foundFailing = false;

        // Binary search for first failing
        while (!foundFirst) {
            if (isBadVersion.isFailingVersion(start)) {
                end = start;
                start /= 2;
                foundFailing = true;
            } else {
                if (foundFailing && isBadVersion.isFailingVersion(start+1)) {
                    start++;
                    foundFirst = true;
                }
                start = (start + end) / 2;
            }
        }

        return start;
    }
}
