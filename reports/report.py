from prometheus_client import CollectorRegistry, Gauge, push_to_gateway
from pymongo import MongoClient
import sys

thisRegistery = CollectorRegistry()
#
client = MongoClient()
db = client.test
cursor = db.chatMessageModel.find()

sys.stdout = open("chat-report.txt", "w")
print("Chat report")
print("-----------------------------")
count = 0
for document in cursor:
    print(document['createDate'] + " " + document['author'] + " " + document['text'])
    count += 1
print("-----------------------------")
print("Total messages: " + str(count))
sys.stdout.close()
#
g = Gauge('total_messages_count', 'Total message count in DB', registry=thisRegistery)
g.set(count)
push_to_gateway('localhost:9091', job='batchA', registry=thisRegistery)
#
