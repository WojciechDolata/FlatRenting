export default class DateUtils {
  public static formatDate(date) {
    return date[2] + "." + date[1] + "." + date[0] + " " + date[3] + ":" + (date[4] < 10 ? "0" + date[4] : date[4]);
  }
}
