export function meeting(meeting, time1, time2, date) {
    this.meeting = meeting;
    this.start_time = time1;
    this.end_time = time2;
    this.date=date
}


meeting.prototype.convert = function (a) {
    var b = this[a + '_time'];
    var start = b.slice(0, 3);
    var min = parseInt(b.slice(3, 5));
    min = a == "start" ? min - 1 : min + 1;
    min = min < 10 ? "0" + min : min;
    var ending = b.slice(5, 8);

    return start + min + ending

}

// eslint-disable-next-line no-extend-native
Array.prototype.getFreeTime = function () {
    var l = this.length,
    withFreeTime = [],
    i = 0;
while (i < l) {

    var s = this[i - 1] ? this[i - 1].convert('end') : "00:00:00";
    withFreeTime.push(new meeting('freetime', s, this[i].convert('start'),this[i].date));
    withFreeTime.push(this[i]);
    i++;
}

    //to add the freetime from "18:16:00" to  "23:59:00":
    // withFreeTime.push(new meeting('freetime', this[i-1].convert('end'),'23:59:00'));

    return withFreeTime;
}

