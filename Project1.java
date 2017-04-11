import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
/**
 * Calendar
 * @author Matin Afkhami
 *
 */
public class Project1 {
    private static String[] monthNamesM = { "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December" };
    private static String[] monthNamesS = { "Farvadin", "Ordibehesht", "Khordad", "Tir", "Mordad", "Shahrivar", "Mehr",
            "Aban", "Azar", "Dey", "Bahman", "Esfand" };
    private static int[] monthDaysM = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static int[] monthDaysS = { 31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29 };
    private static int[] monthArray= new int [42] ;
    private static String RED = "\u001B[31m";
    private static String GREEN = "\u001B[32m";
    private static String YELLOW = "\u001B[33m";
    private static String BLUE = "\u001B[34m";
    private static String PURPLE = "\u001B[35m";
    private static String RESET = "\u001B[0m";
    private static String BLUE_Back = "\u001B[46m";

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) throws Exception {
        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("Screen cleaned due to fix color in some consoles." +
                    " you can disable color (with color off command) if problem exists.\n\n");
        }
        printCalendarBase(0,0);
        Scanner reader = new Scanner(System.in);

        while (true){
            System.out.print( GREEN +System.getProperty("user.name") +"/Calendar> " +RESET);
            int yearNo,monthNo,dayNo;
            String userInput0 = reader.nextLine();
            String userInput = userInput0.replaceAll(" ","");

            if ( userInput.startsWith("clear") ){
                if ( userInput.length()!=5 ){
                    System.out.println("Usage: clear\n\nthis command is used to reset cmd console and few others.\n");
                }else{
                    System.out.println("\nno way to clear this console :(");
                    clearScreen();
                    System.out.println();
                }

            }
            else if ( userInput.startsWith("showcalendar") ){
                if ( userInput.startsWith("current",12) && userInput.length()==19 ){
                    printCalendarBase(0,0);
                }else if ( userInput.length()!=19 && userInput.length()!=18 || userInput.charAt(16)!=',' ){
                    System.out.println("Usage: show calendar [year], [month]\n       show calendar current" +
                            "\n\nthis command is used to show specified calendar date." +
                            "\n\nMost used commands:" +
                            "\n  [year] - an integer number between 1920 to 2100" +
                            "\n  [month] - an integer number between 1 to 12" +
                            "\n  current - to show current month view\n");
                }else{
                    int m = 0;
                    if ( userInput.length()==18 ) m=1;
                    yearNo = getNum(userInput,12,15);
                    monthNo = getNum(userInput,17,18-m);
                    if (yearNo==-1 || monthNo==-1){
                        System.out.println("Invalid input number." +
                                "\nEnter a four digit number for year, and two digit number for month.\n");
                    }else if ( monthNo>12 ){
                        System.out.println("Invalid month input.");
                    }else{
                        printCalendarBase(yearNo, monthNo);
                    }
                }
            }
            else if ( userInput.startsWith("converttogregorian") ){
                int slash1 = userInput.indexOf('/');
                int slash2 = userInput.indexOf('/',slash1+1);
                int slash3 = userInput.indexOf('/',slash2+1);
                if ( userInput.length() < 24 || slash2==-1 || slash3!=-1 ) {
                    System.out.println("Usage: convert to gregorian [year]/[month]/[day]" +
                            "\n\nthis command is used to convert persian date to gregorian." +
                            "\n\nMost used commands:" +
                            "\n  [year] - a one to four digit integer number." +
                            "\n  [month] - an integer number between 1 to 12 for month" +
                            "\n  [day] - an integer number between 1 to maximum of 31\n");
                }else{
                    yearNo = getNum(userInput,18,slash1-1);
                    monthNo = getNum(userInput,slash1+1,slash2-1);
                    dayNo = getNum(userInput,slash2+1,userInput.length()-1);
                    if ( yearNo<1 || monthNo<1 || dayNo<1 ){
                        System.out.println("Invalid input date number." +
                                "\nEnter a valid four digit number for year, and two digit number for month or day.\n");
                    }else if ( dayNo>getMonthDaysS(yearNo,monthNo) ){
                        System.out.println("Invalid day input.\n");
                    }else if ( monthNo>12 ){
                        System.out.println("Invalid month input.\n");
                    }else{
                        convertToGregorian(yearNo,monthNo,dayNo);
                    }
                }
            }
            else if ( userInput.startsWith("converttopersian") ){
                int slash1 = userInput.indexOf('/');
                int slash2 = userInput.indexOf('/',slash1+1);
                int slash3 = userInput.indexOf('/',slash2+1);
                if ( userInput.length() < 22 || slash2==-1 || slash3!=-1 ) {
                    System.out.println("Usage: convert to persian [year]/[month]/[day]" +
                            "\n\nthis command is used to convert gregorian date to persian." +
                            "\n\nMost used commands:" +
                            "\n  [year] - a four digit integer number. example: 0001" +
                            "\n  [month] - an integer number between 01 to 12 for month." +
                            "\n  [day] - an integer number between 01 to maximum of 31\n");
                }else{
                    yearNo = getNum(userInput,16,slash1-1);
                    monthNo = getNum(userInput,slash1+1,slash2-1);
                    dayNo = getNum(userInput,slash2+1,userInput.length()-1);
                    if ( yearNo<1 || monthNo<1 || dayNo<1 ){
                        System.out.println("Invalid input date number." +
                                "\nEnter a valid four digit number for year, and two digit number for month or day.\n");
                    }else if ( dayNo>getMonthDaysM(yearNo,monthNo) ){
                        System.out.println("Invalid day input.\n");
                    }else if ( monthNo>12 ){
                        System.out.println("Invalid month input.");
                    }else if ( getDaysFromBaseC(true,yearNo,monthNo)+dayNo<227261 ){
                        System.out.println("\n" +( 227261-getDaysFromBaseC(true,yearNo,monthNo)-dayNo )
                                +" days before persian calendar start.\n");
                    }else{
                        convertToPersian(yearNo, monthNo, dayNo);
                    }
                }
            }
            else if ( userInput.startsWith("exit") ){
                if ( userInput.length()!=4 ){
                    System.out.println("Usage: exit\n\nthis command is to exit calendar program.\n");
                }else{
                    break;
                }
            }
            else if ( userInput.startsWith("coloron") ){
                if ( userInput.length()!=7 ){
                    System.out.println("Usage: color on" +
                            "\n\nthis command enable colors.\n");
                }else{
                    color(true);
                }
            }
            else if ( userInput.startsWith("coloroff") ){
                if ( userInput.length()!=8 ){
                    System.out.println("Usage: color off" +
                            "\n\nthis command disable colors, in order to fix problems with some consoles.\n");
                }else{
                    color(false);
                }
            }
            else{
                System.out.println( userInput0 +": command not found" +
                        "\n\nMost used commands:" +
                        "\n  show calendar [year], [month] - show specified calendar date" +
                        "\n  show calendar current - show current calendar date" +
                        "\n  convert to gregorian [year]/[month]/[day] - convert persian date to gregorian" +
                        "\n  convert to persian [year]/[month]/[day] - convert gregorian date to persian" +
                        "\n  clear - reset console" +
                        "\n  exit - exit calendar program" +
                        "\n  color on - enable color" +
                        "\n  color off - disable color, in order to fix problems with some consoles");
            }
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void color(boolean choice) {
        RED = (choice)? "\u001B[31m" : "";
        GREEN = (choice)? "\u001B[32m" : "";
        YELLOW = (choice)? "\u001B[33m" : "";
        BLUE = (choice)? "\u001B[34m" : "";
        PURPLE = (choice)? "\u001B[35m" : "";
        RESET = (choice)? "\u001B[0m" : "";
        BLUE_Back = (choice)? "\u001B[46m" : "";
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static int getNum(String userInput, int startPoint, int endPoint){
        int num=0;

        for (int i=startPoint ; i<=endPoint ; i++){
            if ( userInput.charAt(i)<'0' || userInput.charAt(i)>'9' ){
                return -1;
            }else{
                num += (userInput.charAt(i) - '0') * Math.pow(10,endPoint-i);
            }
        }

        if ( (startPoint==12 && (num<1920 || num>2100) ) || (startPoint==17 && (num<1 || num>12) ) ){
//12 means year in show calendar command,17 means month in show calendar command
            return -1;
        }else{
            return num;
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static boolean isLeapYearGregorian(int yearNo){
        return ( ( (yearNo % 4 == 0) && (yearNo  % 100 != 0) ) || ( yearNo % 400 == 0 ) );
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static boolean isLeapYearPersian(int year){
        double a = 0.025;
        int b = 266;
        double leapDays0,leapDays1;
        if (year > 0) {
            leapDays0 = ((year + 38) % 2820) * 0.24219 + a;
            leapDays1 = ((year + 39) % 2820) * 0.24219 + a;
        }else if(year < 0){
            leapDays0 = ((year + 39) % 2820) * 0.24219 + a;
            leapDays1 = ((year + 40) % 2820) * 0.24219 + a;
        }else{
            return true;
        }
        int frac0 = (int)((leapDays0 - (int)leapDays0)*1000);
        int frac1 = (int)((leapDays1 - (int)leapDays1)*1000);

        return (frac0 <= b && frac1 > b);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static int getMonthDaysM(int yearNo, int monthNo){

        if (monthNo==0){
            yearNo--;
            monthNo=12;
        }
        if ( isLeapYearGregorian(yearNo) && monthNo == 2){
            return 29;
        }else{
            return Project1.monthDaysM[monthNo - 1];
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static int getMonthDaysS(int yearNo, int monthNo){

        if (monthNo==0){
            yearNo--;
            monthNo=12;
        }
        if ( isLeapYearPersian(yearNo) && monthNo == 12){
            return 29;
        }else{
            return monthDaysS[monthNo - 1];
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static int getDaysFromBase(int yearNo, int monthNo){

        int daysFromBase = 0;
        for (int i=1920; i<yearNo; i++){
            if (isLeapYearGregorian(i)){
                daysFromBase += 366;
            }
            else daysFromBase += 365;

        }

        for (int i=1; i<monthNo; i++){
            daysFromBase += getMonthDaysM(yearNo,i);
        }

        return daysFromBase ;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static int getDaysFromBaseC(boolean isGre, int yearNo, int monthNo){

        int daysFromBase = yearNo*365;
        daysFromBase += leapYearCounter(isGre,yearNo);
        for (int i=1; i<monthNo; i++){
            daysFromBase += (isGre)? getMonthDaysM(yearNo, i) : getMonthDaysS(yearNo, i);
        }
        return daysFromBase ;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static int leapYearCounter(boolean isGre,int yearNo) {

        int count=0;
        for (int i=1;i<yearNo;i++){
            if ((isGre && isLeapYearGregorian(i)) || (!isGre && isLeapYearPersian(i)) ) {
                count++;
            }
        }
        return count;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static int getDayOfWeek(int yearNo, int monthNo){

        switch(getDaysFromBase(yearNo,monthNo) % 7){
            case 0:
                return 5;
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
                return 4;
            default:
                return -1;
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static String getDayOfDateC(boolean isGre, int yearNo, int monthNo, int dayNo){

        switch( (dayNo+getDaysFromBaseC(isGre,yearNo,monthNo)) %7 ){
            case 0:
                return (isGre)? "Sat" : "Wed";
            case 1:
                return (isGre)? "Sun" : "Thu";
            case 2:
                return (isGre)? "Mon" : "Fri"; // Gregorian calendar starts on monday and Persian starts on friday
            case 3:
                return (isGre)? "Tue" : "Sat";
            case 4:
                return (isGre)? "Wed" : "Sun";
            case 5:
                return (isGre)? "Thu" : "Mon";
            case 6:
                return (isGre)? "Fri" : "Tue";
            default:
                return "";
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void printCalendarBase(int yearNo, int monthNo){

        int currentYear = LocalDateTime.now().getYear();
        int currentMonth = LocalDateTime.now().getMonthValue();

        if ( yearNo==0 ){
            yearNo = currentYear;
        }
        if ( monthNo==0 ){
            monthNo = currentMonth;
        }

        int dayOfMonth = 0;
        boolean isDateCurrent = false;
        if ( yearNo==currentYear && monthNo==currentMonth ){
            dayOfMonth = LocalDateTime.now().getDayOfMonth();
            isDateCurrent = true;
        }

        for ( int i=0 ; i<42 ; i++ ){
            monthArray[i] = 0;
        }
        for ( int i=0 ; i<getDayOfWeek(yearNo,monthNo)-1 ; i++ ){
            monthArray[i] = i+getMonthDaysM(yearNo,monthNo-1)-getDayOfWeek(yearNo,monthNo)+2;
        }
        for ( int i=1 ; i<getMonthDaysM(yearNo,monthNo)+1 ; i++ ){
            monthArray[i+getDayOfWeek(yearNo,monthNo)-2] = i;
        }
        for ( int i=0,k=1 ; i<42 ; i++ ){
            if ( monthArray[i]==0 ){
                monthArray[i] = k ;
                k++ ;
            }
        }

        System.out.println(YELLOW +"\n" +monthNamesM[monthNo-1] +" " +yearNo +"\n" +RESET);
        System.out.println( PURPLE +"Su   Mo   Tu   We   Th   Fr   Sa\n" +BLUE);
        for ( int i=1,k=-1 ; i<43 ; i++ ){

            if ( monthArray[i-1]==1 ){
                k*=-1;
            }

            if ( isDateCurrent && k==1 && i-getDayOfWeek(yearNo,monthNo)+1==dayOfMonth ){
                System.out.print(BLUE_Back);
                printCalendar(i,true);
                System.out.print( RESET );
            }
            else if ( k==-1 ){
                System.out.print( BLUE );
                printCalendar(i,false);
                System.out.print( RESET );
            }
            else if ( (i==1 || i%7==0 || i==8 || i==15 || i==22 || i==29 || i==36) ){
                System.out.print( RED );
                printCalendar(i,false);
                System.out.print( RESET );
            }
            else{
                printCalendar(i,false);
            }
        }
        System.out.println("\n\n");
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void printCalendar(int i,boolean isCurrentDay){

        if ( monthArray[i-1]>0 && monthArray[i-1]<10 ){
            System.out.print(" ");
        }
        if (isCurrentDay){
            System.out.print( BLUE_Back +monthArray[i-1] +RESET +"   " );
        }else{
            System.out.print( monthArray[i-1] +"   " );
        }
        if ( i%7==0 ){
            System.out.println("\n");
        }

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void convertToGregorian(int persianYear,int persianMonth,int persianDay){

        int gregorianYear,gregorianMonth=0;
        int x = ( isLeapYearPersian(persianYear) )? 12 : 11;
        gregorianYear = (persianMonth<10 || (persianMonth==10 && persianDay<x))? persianYear+621 : persianYear+622 ;
        int daysFromBaseS = getDaysFromBaseC(false,persianYear,persianMonth)+persianDay+227260;
        daysFromBaseS -= leapYearCounter(true,gregorianYear);
        int remain = (daysFromBaseS%365);

        while (true){
            gregorianMonth++;
            if ( remain < getMonthDaysM(gregorianYear,gregorianMonth) ){
                break;
            }else{
                remain -= getMonthDaysM(gregorianYear,gregorianMonth);
            }
        }
        System.out.println( RED +"\n" +getDayOfDateC(true,gregorianYear,gregorianMonth,remain) +", "
                +monthNamesM[gregorianMonth-1] +" "+remain +", " +gregorianYear +"\n" +RESET);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void convertToPersian(int gregorianYear,int gregorianMonth,int gregorianDay){

        int persianYear,persianMonth=0;
        int x = ( isLeapYearGregorian(gregorianYear) )? 20 : 21;
        int y = ( isLeapYearGregorian(gregorianYear) )?  1 :  0;
        persianYear =(gregorianMonth<3||(gregorianMonth==3&&gregorianDay<x))? gregorianYear-622-y : gregorianYear-621-y;
        int daysFromBaseM = getDaysFromBaseC(true,gregorianYear,gregorianMonth)+gregorianDay-227260;
        daysFromBaseM -= leapYearCounter(false,persianYear);
        int remain = (daysFromBaseM%365);

        while (true){
            persianMonth++;
            if ( remain < getMonthDaysS(persianYear,persianMonth) ){
                break;
            }else{
                remain -= getMonthDaysS(persianYear,persianMonth);
            }
        }
        System.out.println( RED +"\n" +getDayOfDateC(false,persianYear,persianMonth,remain) +", "
                +monthNamesS[persianMonth-1] +" "+remain +", " +persianYear +"\n" +RESET);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void clearScreen() throws IOException, InterruptedException {

        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }else {
            Runtime.getRuntime().exec("clear");
        }
    }
}