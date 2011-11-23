(ns grotesql.output)

(defn stdout
	"Prints table to standard output"
	[ table ]
	 (println (apply str (interpose "\n" (map (fn [row] (apply str (interpose ", " (vals row)))) table)))))
