(ns grotesql.runner
 (:use [grotesql data output functions core]))

;;;;;;;;;;;;; Create some curried functions used by example1:
; drop column :fname
(defnode drop-fname drop-column [:fname])

; drop column :lname
(defnode drop-lname drop-column [:lname])

; merge :fname and :lname into :fullname
(defnode create-fullname str-columns :fname :lname :fullname " ")

; Create fake input 1:
(defnode fake-input1 emulate-input table1)

; Create fake input 2:
(defnode fake-input2 emulate-input table2)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Example - merge names to full name, and drop fname+lname column
; then print to stdout
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; create pipe
(def example1-list [fake-input1 create-fullname drop-lname drop-fname stdout])
(defpipe example1-pipe example1-list)

; run it
(example1-pipe)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Example - program involving multi-input node
; One pipe is (read input, drop :fname)
; Other pipe is (read input2, merge country-city, drop country, drop city)
; Final pipe is a concat that takes the two pipes, drops age, and prints

; Extra functions:
(defnode drop-country drop-column [:country])
(defnode drop-city drop-column [:city])
(defnode drop-age drop-column [:age])
(defnode create-location str-columns :city :country :location ", ")

; create input2 pipe
(def input2-list [fake-input2 create-location drop-country drop-city])
(defpipe input2-pipe input2-list)
; create primary pipe, the 
(def input1-list [fake-input1 drop-fname (partial concat-tables (input2-pipe)) drop-age stdout])
(defpipe input1-pipe input1-list)

; run it
(input1-pipe)

