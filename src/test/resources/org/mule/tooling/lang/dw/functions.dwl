%dw 2.0
var x=(param1, param2) -> { "$param1": param2 }
var y=(param1, param2 = "c") -> { "$param1": param2 }
var toUser = (user) -> { name: user.name,	lastName: user.lastName }
fun z(param1, param2) = { "$param1": param2 }
var a = { name: "Mariano" , toUser: ((param1, param2) -> { "$param1": param2 }) }
var applyFirst =  (array, func) -> (func(array[0]) ++  array[(1 to -1)])
var value = null
fun f2(a1, a2) = ""
---
result: {
  a: x("a", "b"),
  b: y("a"),
  c: y("a", "b"),
  users: { (in1 map ((user) -> { user: (toUser(user) ++ user) })) },
  d: z("a", "b"),
  e: a.toUser("name","Mariano"),
  f: a.toUser("name","Mariano").name,
  f: applyFirst("mariano", (s) -> upper(s) ),
  g: [] map (s) -> upper(s),
  h: 1 f2 2,
  i:  (() -> " the $(value) is")()

}
