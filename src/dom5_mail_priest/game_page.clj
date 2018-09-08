(ns dom5-mail-priest.game-page
  (require [net.cgrand.enlive-html :as html]))


(defn -game-exists? [game-page-content]
  (not= "Sorry, this isn't a real game. Have you been messing with my URL?"
        (->
         (html/select game-page-content [:body])
         (first)
         (:content)
         (reverse)
         (second))))


(defn -parse-gen-info [game-page-content]
  (let [raw (->> (html/select game-page-content [:body])
                 (first)
                 (:content)
                 (take 14)
                 (drop 9))]

    {:game-title (first (:content (first raw)))
     :turn-or-age (nth raw 2)
     :next-or-players (nth raw 4)}))


(defn parse-game-page [title]
  (let [page (html/html-resource
              (java.net.URL. (str "http://www.llamaserver.net/gameinfo.cgi?game=" title)))]
    (if (-game-exists? page)
      (-parse-gen-info page)
      nil)))
