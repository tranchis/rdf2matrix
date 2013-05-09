(ns rdf2matrix)

(use 'plaza.rdf.core)
(use 'plaza.rdf.implementations.jena)

(init-jena-framework)

(defn stmt-to-tuple [stmt]
  {:subject (.getSubject stmt) :predicate (.getPredicate stmt) :object (.getObject stmt)})

(defn get-all-statements [file-name]
  (let [resource (clojure.java.io/resource file-name)
      file (clojure.java.io/as-file resource)
      fis (java.io.FileInputStream. file)
      jm (document-to-model fis :xml)
      stmts (-> jm to-java .listStatements iterator-seq)]
    (pmap stmt-to-tuple stmts)))

(let [triples (get-all-statements "rollingstones.rdf")
      subjects (into #{} (map :subject triples))
      objects (into #{} (map :object triples))
      predicates (into #{} (map :predicates triples))]
  ;; With last.fm, only the rdf:type predicate is being used, so let's ignore it by now
  (clojure.combinatorics/cartesian-product '(0 0) '(0 0)))

