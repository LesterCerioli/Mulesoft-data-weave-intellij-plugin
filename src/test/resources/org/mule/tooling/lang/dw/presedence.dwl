%dw 2.0
---
{
    a: in0 as Number >= in0,
    b: in0 <= in1  + 1,
    c: in0 is Number and in0,
    d: 1 + 3 / 5 - 10 * 8,
    e: [[],[]] map sizeOf $ reduce ($$ ++ $)
}