class IP2CIDR {
    long ipToLong(String ip) {
        String[] arr = ip.split("\\.");
        long val = 0;
        int shift = 24;
        for(String s: arr){
            System.out.println(s +" " + Long.valueOf(s) + " " + (Long.valueOf(s) << shift));
            val += (Long.valueOf(s) << shift);
            shift -= 8;
        }
        Integer.highestOneBit(3);
        return val;
    }
    String longToIp(long val) {
        StringBuilder sb = new StringBuilder();
        int shift = 24;
        sb.append((val & 0xff000000) >> 24);
        sb.append(".");
        sb.append((val & 0x00ff0000) >> 16);
        sb.append(".");
        sb.append((val & 0x0000ff00) >> 8);
        sb.append(".");
        sb.append(val & 0x000000ff);
        return sb.toString();
    }
    public List<String> ipToCIDR(String startIp, int range) {
        List<String> res = new ArrayList<>();
        long startVal = ipToLong(startIp);
        long endVal = startVal + range - 1;
        String s = "";
        while(startVal <= endVal) {
            long locOfFirstOne = startVal & -startVal;
            int curMask = 32 - (int) (Math.log(locOfFirstOne) / Math.log(2));

            double currRange = Math.log(endVal - startVal + 1) / Math.log(2);
            int currRangeMask = 32 - (int) Math.floor(currRange);

            String ip = longToIp(startVal);

            curMask = Math.max(currRangeMask, curMask);
            res.add(ip + "/" + curMask);

            startVal += Math.pow(2, (32 - curMask));
        }

        return res;
    }

    public List<String> ipToCIDR2(String ip, int n) {
        long start = 0;
        for(String s : ip.split("\\.")) start = start * 256 + Integer.parseInt(s);
        List<String> ans = new ArrayList<>();
        while(n > 0) {
            int num = Math.min((int) (start & -start), Integer.highestOneBit(n));
            ans.add(String.format("%s.%s.%s.%s/%s", start >> 24, (start >> 16) % 256, (start >> 8) % 256, start % 256, 32-Integer.numberOfTrailingZeros(num)));
            start += num;
            n -= num;
        }
        return ans;
    }
}
public class IP2CIDRTest {
    public static void main (String[] args) {

        String ip = "255.0.0.7";
        int n = 10;

        IP2CIDR ip2Cidr = new IP2CIDR();
        long val = ip2Cidr.ipToLong(ip);
        System.out.println("--> " + val);
        System.out.println(ip2Cidr.longToIp(val));
        List<String> ipToCidr = ip2Cidr.ipToCIDR(ip, 288);
        for (String s : ipToCidr) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
