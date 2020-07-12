import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SalaryInfoTest {
    private static final String[] ROLES = {"Сергей", "Андрей", "София"};
    private static final String[] SCRIPT_ARRAY = {
            "25.04.2019 Сергей 60 50",
            "25.04.2019 Андрей 3 200",
            "25.04.2019 София 10 100",

            "26.04.2019 Андрей 3 200",
            "26.04.2019 София 9 100",

            "27.04.2019 Сергей 7 100",
            "27.04.2019 София 3 80",
            "27.04.2019 Андрей 8 100"
    };
    private static final String[] DATES = {
            "24.04.2019",
            "25.04.2019",
            "26.04.2019",
            "27.04.2019"
    };
    private static final String[] EXPECTED_REPORTS = {
            "Отчёт за период 24.04.2019 - 24.04.2019\n" +
                    "Сергей - 0\n" +
                    "Андрей - 0\n" +
                    "София - 0",
            "Отчёт за период 24.04.2019 - 25.04.2019\n" +
                    "Сергей - 3000\n" +
                    "Андрей - 600\n" +
                    "София - 1000",
            "Отчёт за период 24.04.2019 - 26.04.2019\n" +
                    "Сергей - 3000\n" +
                    "Андрей - 1200\n" +
                    "София - 1900",
            "Отчёт за период 24.04.2019 - 27.04.2019\n" +
                    "Сергей - 3700\n" +
                    "Андрей - 2000\n" +
                    "София - 2140"
    };

    private static final String[] SECOND_SCRIPT_ARRAY = {
            "13.07.2019 Сергей 60 50",
            "15.07.2019 Андрей 3 200",
            "15.07.2019 София 10 100",

            "16.07.2019 Андрей 3 200",
            "16.07.2019 София 9 100",

            "10.08.2019 Сергей 7 100",
            "08.08.2019 София 3 80",
            "11.08.2019 Андрей 8 100"
    };
    private static final String[] SECOND_DATES = {
            "14.07.2019",
            "10.08.2019",
    };
    private static final String SECOND_EXPECTED_REPORTS =
            "Отчёт за период 14.07.2019 - 10.08.2019\n" +
                    "Сергей - 700\n" +
                    "Андрей - 1200\n" +
                    "София - 2140";

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void getSalaryInfoByMonth() throws Exception {
        try {
            Class<?> exceptionClass = Class
                    .forName("exception.IllegalDateParametersException");
            expectedEx.expect((Class<? extends Throwable>) exceptionClass);
            expectedEx.expectMessage("Wrong parameters");
            SalaryInfo salary = new SalaryInfo();
            for (int i = 0; i < DATES.length; i++) {
                String actualResult = salary.getSalaryInfo(ROLES, SCRIPT_ARRAY, DATES[0], DATES[i]);
                String expectedResult = SalaryInfoTest.EXPECTED_REPORTS[i];

                Assert.assertEquals(
                        "Test failed from date " + DATES[0] + " to " + DATES[i]
                                + "\nactual: \n" + actualResult
                                + "\nexpected: \n" + expectedResult,
                        expectedResult,
                        actualResult);
            }
            salary.getSalaryInfo(ROLES, SCRIPT_ARRAY, DATES[1], DATES[0]);
        } catch (ClassNotFoundException e) {
            Assert.fail("Should create a class called 'IllegalDateParametersException'.");
        }
    }

    @Test
    public void getSalaryInfoByTwoMonths() throws Exception {
        SalaryInfo salary = new SalaryInfo();
        String actualResult = salary.getSalaryInfo(ROLES, SECOND_SCRIPT_ARRAY,
                SECOND_DATES[0], SECOND_DATES[1]);
        String expectedResult = SalaryInfoTest.SECOND_EXPECTED_REPORTS;

        Assert.assertEquals(
                "Test failed from date " + SECOND_DATES[0] + " to " + SECOND_DATES[1]
                        + "\nactual: \n" + actualResult
                        + "\nexpected: \n" + expectedResult,
                expectedResult,
                actualResult);
    }

    @Test
    public void testClassExists() {
        try {
            Class.forName("exception.IllegalDateParametersException");
        } catch (ClassNotFoundException e) {
            Assert.fail("Should create a class called 'IllegalDateParametersException'.");
        }
    }
}
