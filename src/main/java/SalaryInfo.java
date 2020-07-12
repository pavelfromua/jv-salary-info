import exception.IllegalDateParametersException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getSalaryInfo(String[] names, String[] data, String dateFrom,
                                String dateTo) throws Exception {
        LocalDate dateFromLocal = LocalDate.parse(dateFrom, formatter);
        LocalDate dateToLocal = LocalDate.parse(dateTo, formatter);

        if (dateFromLocal.isAfter(dateToLocal)) {
            throw new IllegalDateParametersException("Wrong parameters");
        }

        StringBuilder result = new StringBuilder("Отчёт за период " + dateFrom + " - " + dateTo);
        result.append("\n");

        int index = 0;
        for (String name: names) {
            int salary = 0;
            index++;

            for (String line: data) {
                String[] items = line.split(" ");

                LocalDate dateItem = LocalDate.parse(items[0], formatter);
                String nameItem = items[1];
                int hoursItem = Integer.parseInt(items[2]);
                int rateItem = Integer.parseInt(items[3]);

                if (name.equals(nameItem) && ((dateItem.isAfter(dateFromLocal)
                        | dateItem.equals(dateFromLocal))
                        && (dateItem.isBefore(dateToLocal) | dateItem.equals(dateToLocal)))) {
                    salary += hoursItem * rateItem;
                }
            }

            result.append(name);
            result.append(" - ");
            result.append(salary);

            if (index < names.length) {
                result.append("\n");
            }
        }

        return result.toString();
    }


}
