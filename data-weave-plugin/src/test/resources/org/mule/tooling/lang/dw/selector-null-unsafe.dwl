{
  a: in0.b.c.d!,
  b: in0['b']['c']['d']!,
  c: in0['b'].c['d'].e!,
  d: in0.b[0][1]!,
  e: ['Mariano'][0]!,
  f: { name: "Mariano", age: 2 }['age']!,
  g: { name: "Mariano", age: 2 }.age!,
  h: { name: "Mariano", age: 2 }[(in0.b)]!,
  i: { name: "Mariano", age: 2 }[(in0.b map $.b)]!,
  j: in0[(0 to 4)]!,
  k: in0.b[@'c']!,
  l: in0.b.@c!
}