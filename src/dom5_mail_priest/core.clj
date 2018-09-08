(ns dom5-mail-priest.core
  (:use seesaw.core)
  (:import org.pushingpixels.substance.api.SubstanceLookAndFeel)
  (:gen-class)
  (:require [dom5-mail-priest.game-page :as gp]))


(def help-text "A placeholder for help text")


(def about-text "Llamabeasts are native to Mictlan jungles,
and were also known to be tamed by the Kingdom's priesthood.

As the ancient scrolls reveal, tamed beasts were extensively
used for messaging throughout all the Dominion territories...")


(def game-query-label (label :text "Search for a game on Llamaserver" :font "MONOSPACE-BOLD-16"))


(def game-search-button (button :text "Open Search" :font "MONOSPACE-BOLD-16"))


(defn game-info-frame []  ;; Why do I get errors on cancel?
  (let [game-info (gp/parse-game-page (input "Enter game title query"
                                             :value "GameTitle"))]
    (if game-info
      (-> (frame :title (game-info :game-title)
                 :content (vertical-panel
                           :items [(label :text (game-info :game-title)
                                          :font "ARIAL-BOLD-18"
                                          :border 15)

                                   (label :text (game-info :turn-or-age)
                                          :font "ARIAL-16"
                                          :border 10)

                                   (label :text (game-info :next-or-players)
                                          :font "ARIAL-16"
                                          :border 10)]))
          pack! show!)
      (alert "Game not found!"))))


(listen game-search-button :action (fn [e] (game-info-frame)))


(defn -main [& args]
  ;; Set GUI skin that seems well-suited for the app
  (SubstanceLookAndFeel/setSkin
   "org.pushingpixels.substance.api.skin.DustSkin")

  (invoke-later
   (->
    (frame
     :title "Hawthorne's Dom5 Mail Priest"
     ;; :on-close :hide
     :on-close :exit
     :content (tabbed-panel :placement :top :overflow :scroll
                            :font "ARIAL-BOLD-18"
                            :tabs [{:title "Game Searcher"
                                    :tip   "Experimental - search for a game at llamaserver.net"
                                    :content (flow-panel
                                              :align :left
                                              :hgap 20
                                              :items [game-query-label game-search-button]
                                              )}

                                   {:title "Help"
                                    :tip   "Hints and program usage how-to"
                                    :content (text :multi-line? true
                                                   :editable? false
                                                   :text help-text
                                                   :border 5
                                                   :font "ARIAL-BOLD-18"
                                                   )}

                                   {:title "About"
                                    :tip   "About teh Program"
                                    :content (text :multi-line? true
                                                   :editable? false
                                                   :text about-text
                                                   :border 5
                                                   :font {:name "ARIAL" :size 16})}]))
    pack!
    show!)))
