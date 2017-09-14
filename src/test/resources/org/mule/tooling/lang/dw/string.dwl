var separator = ""
---
{
  a: "A very long string"[(0 to 8)],                               // "A very lo" (right)
  b: "A very long string"[(-6 to -1)],                             // "string" (left)
  c: sizeOf("A very long string"),                              // 18 (length)
  d: "A very long string"[10],                                 // n (charAt)
  e: "A" ++ " " ++ "very" ++ " " ++ "long" ++ " " ++ "string", // "A very long string" (concat)
  f: "A very long string" matches /^Mariano/,                  // false (startsWith)
  g: "A very long string" matches /Achaval$/,                  // false (endsWith)
  h: "A very long string" contains "str",                      // true (contains)
  i: "A very long string" splitBy /\ /,                           // ["A", "very", "long", "string"] (splitBy)
  j: "abcdefg" splitBy /[ce]/,                                   // ["ab", "d", "fg"] (splitBy)
  k: "aabccde" match /(a).(b)(c.)d/,                               // [ "a", "b", "cc" ] (matchGroups)
  k: "aabccde" find /(a).(b)(c.)d/,                              // [ 0, 1, 2 ] (matchIndex)
  m: ("aabccdbce" find /bc/)[0],                                 // 2 (findFirstOf)
  n: ("aabccdbce" find /bc/)[-1],                                // 6 (findLastOf)
  o: trim("   aabccdbce   "),                                   // "aabccdbce" (trim)
  p: upper("aabccdbce"),                                        // "AABCCDBCE" (uppercase)
  q: lower("AABCCDBCE"),                                         // "aabccdbce" (lowercase)
  r: "Mariano" startsWith "Em",
  s: "Emiliano" endsWith "no",
  t: "EmiEmi" scan /(mi)/,
  u: ["Emi","Emi"] joinBy ",",
  v: in0.list joinBy ",",
  w: in0.list joinBy separator,
  x: "A"++" "++"very"++" "++"long"++" "++"string",
  y: "A very long string"matches/Achaval$/,
  z: "A very long string"matches /Achaval$/,
  a1: "A very long string" matches/Achaval$/
}