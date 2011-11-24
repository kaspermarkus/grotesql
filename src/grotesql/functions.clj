(ns grotesql.functions)

; TODO:
; * Type checking - all keys supplied has to be symbols


; This should be easily changeable to include multiple keys - only need to find the damn function/macro that changes '(:a :b :c) to :a :b :c ... grrrrr.
(defn drop-column
	"Takes a single key and a table and drops the columns that has the names of the keys"
	[ key table ]
	(map (fn [row] (dissoc row key)) table))

; TODO/FIXME: 
; * Make sure that we do not supply a key already in use (newkey cannot be a key used in table)
; * newkey has to be a symbol
(defn str-columns
	"Takes a table and three keys. For each row, the values with the two keys are merged into a new column with the new keyname"
	[ key1 key2 newkey separator table ]
	 (map (fn [row] (merge {newkey (str (get row key1) separator (get row key2))} row)) table))

; A test multi input function.. takes two inputs - simply append one table to the other
; One table should be curried by partial
; require tables to have the same keys
; TODO: Currently doesn't merge columns with columns.. not sure it matters, but ordering is off!!!!
(defn concat-tables
	"Takes two tables and returns the second concatenated to the first. Requires the two tables to have identical keys"
	[ table1 table2 ]
	;{:pre [(= (keys (sort (first table1))) (keys (sort (first table2))))]}
	(concat table1 table2)) 

(defn create-pipe 
	"Compact a list of functions to a pipe and return a new function that is a comp of all the functions in the list and only takes one parameter - the table to run through the pipe. Functions are executed from left to right - first entry in list executed first, that's passed to second, etc."
	[ function-list ]
	; perhaps a check that all entries are functions requiring one parameter?
	(apply comp function-list))

(defn emulate-input
	"Emulates input"
	[ input-table ]
	input-table)


