learnenglishonline
==================

backend code for learn english online program. It's a small program that is used to learn english words and guess them.
curl for add a word:

curl -i -H "Content-type:application/json" -X POST http://learnenglishonline.herokuapp.com/words -d '{"englishValue":"knifle","type":"noun","bulgarianValues:["¿¿¿"]"}
