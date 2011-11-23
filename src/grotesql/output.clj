(ns grotesql.output)

(defn stdout
	"Prints table to standard output"
	[ table ]
	 (println (apply str (interpose "\n" (map (fn [tbl] (apply str (interpose ", " (vals tbl)))) table)))))
