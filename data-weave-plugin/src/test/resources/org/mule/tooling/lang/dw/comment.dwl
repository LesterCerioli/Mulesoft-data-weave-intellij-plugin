// Comment start
%dw 2.0
var pi=3.14 // Another comment
// Another comment
var x=4
// Another comment
---
// This is a comment
{
    // This is another comment
    a: "a",
    b: [ // Yet another comment
        0, 1,
// Comment that starts at the beginning of the line
        "2": {
            i: "i",
            ii: "ii", // Is this a valid comment?
            iii: [ 0, 1, 2],
            iv: { "0": 0, "1": 1 } // Is this a valid comment?
        },
        3
    ]
}