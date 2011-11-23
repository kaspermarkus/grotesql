(ns grotesql.functions)

; TODO:
; * Type checking - all keys supplied has to be symbols


; This should be easily changeable to include multiple keys - only need to find the damn function/macro that changes '(:a :b :c) to :a :b :c ... grrrrr.
(defn drop-column
	"Takes a single key and a table and drops the columns that has the names of the keys"
	[ key table ]
	(map (fn [row] (dissoc row key)) table) 

; TODO/FIXME: 
; * Make sure that we do not supply a key already in use (newkey cannot be a key used in table)
; * newkey has to be a symbol
(defn str-columns
	"Takes a table and three keys. For each row, the values with the two keys are merged into a new column with the new keyname"
	[ key1 key2 newkey separator table ]
	 (map (fn [row] (merge {newkey (str (get row key1) " " (get row key2))} row)) table))
