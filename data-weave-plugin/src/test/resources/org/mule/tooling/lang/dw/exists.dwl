{
  a: in1.b.c.d?,
  b: in1['b']['c']['d']?,
  c: in1['b'].c['d'].e?,
  d: in1.b[0][1]?,
  e: ['Mariano'][0]?,
  f: { name: "Mariano", age: 2 }['age']?,
  g: { name: "Mariano", age: 2 }.age?,
  h: { name: "Mariano", age: 2 }[(in1.b)]?,
  i: { name: "Mariano", age: 2 }[(in1.b map $.b)]?,
  j: in1[(0 to 4)]?,
  k: in1.b[@'c']?,
  l: in1.b.@c?
}