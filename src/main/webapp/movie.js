function changeText(evt) {

    evt.preventDefault();
    let url = "api/movie/all";
    fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log("data", data);
                document.getElementById("result").innerHTML = "<table>" + makeTable(data) + "</table>";
            });
}

function makeTable(objects) {
    var headers = Object.getOwnPropertyNames(objects[0]);
    var result = "<tr>";
    headers.forEach(function (str) {
        result += "<th style='padding-left: 20px'>" + str + "</th>";
    });
    result += "</tr>";
    objects.forEach(function (object) {
        result += "<tr>";
        for (const[key, value] of Object.entries(object)) {
            result += "<td style='padding-left: 20px'>" + value + "</td>";
        }
        ;
        result = result.substring(0, result.length - 2);
        result += "</tr>";
    });
    return result;
}

document.getElementById("button").onclick = changeText;
document.getElementById("clear").onclick = function () {
    document.getElementById("result").innerHTML = "";
};

