import pandas as pd
import logging
import threading
from yahoo_fin import stock_info as si
from time import sleep

intThread = 0

def stop_loop():
    global intThread
    intThread = 1
    sleep(2)
    intThread = 0
	

def getStockData(stock, timeFrame, fromDate, toDate):
    if(fromDate == "" and toDate == ""):
        thisData = si.get_data(stock, interval=timeFrame)
    else:
        thisData = si.get_data(stock, interval=timeFrame, start_date=fromDate, end_date=toDate)

    if(len(thisData) <= 35):
        print("NOT SUITABLE")
        return None

    thisData["MA50"] = thisData["close"].rolling(window=50).mean()
    thisData["MA200"] = thisData["close"].rolling(window=200).mean()

    return thisData

def scanStock(df, strategy):
    #unused variables to be implemented later
    goldenCross = False
    deathCross = False

    #Check for our criteria and mark them true if they're met.
    if len(df) >= 220:
        #Checking for golden cross within the last 2 candles
        if((strategy == "Up" or strategy == "up")
           and
           (df["MA50"][len(df)-2] <= df["MA200"][len(df)-2] and df["MA50"][len(df)-1] > df["MA200"][len(df)-1])
           or
           (df["MA50"][len(df)-3] <= df["MA200"][len(df)-3] and df["MA50"][len(df)-2] > df["MA200"][len(df)-2])
           or
           (df["MA50"][len(df)-4] <= df["MA200"][len(df)-4] and df["MA50"][len(df)-3] > df["MA200"][len(df)-3])
           or
           (df["MA50"][len(df)-5] <= df["MA200"][len(df)-5] and df["MA50"][len(df)-4] > df["MA200"][len(df)-4])
          ):
            print("THIS ONE1")
            goldenCross = True

        elif((strategy == "Down" or strategy == "down")
           and
           (df["MA50"][len(df)-2] >= df["MA200"][len(df)-2] and df["MA50"][len(df)-1] < df["MA200"][len(df)-1])
           or
           (df["MA50"][len(df)-3] >= df["MA200"][len(df)-3] and df["MA50"][len(df)-2] < df["MA200"][len(df)-2])
           or
           (df["MA50"][len(df)-4] >= df["MA200"][len(df)-4] and df["MA50"][len(df)-3] < df["MA200"][len(df)-3])
           or
           (df["MA50"][len(df)-5] >= df["MA200"][len(df)-5] and df["MA50"][len(df)-4] < df["MA200"][len(df)-4])
          ):
            print("THIS ONE2")
            deathCross = True

    return goldenCross, deathCross

def checkStocks(curList, timeFrame, strategy, fromDate, toDate, loopIters):
    iters = 0

    stockListStr = ""

    if(curList == "sp"):
        stockList = si.tickers_sp500()
    elif(curList == "dow"):
        stockList = si.tickers_dow()
    elif(curList == "nsdq"):
        stockList = si.tickers_nasdaq()

    for i in range(len(stockList)):
        iters+=1
        print("intThread is ", intThread)

        goodVolume = False
        goldenCross = False
        deathCross = False

        stock = stockList[i]
        print(stock)
        #sleep(0.3)

        #choose timeframe based on parameters
        if(timeFrame == 'mo' or timeFrame == 'wk' or timeFrame == "d"):
            interval = '1' + timeFrame
            df = getStockData(stock, interval, fromDate, toDate)
            if(df is None):
                continue

            if((timeFrame == 'wk' and df["volume"][len(df)-2] > 2000000) or (timeFrame == 'mo' and df["volume"][len(df)-2] > 8500000) or (timeFrame == 'd' and df["volume"][len(df)-2] > 400000)):
                goodVolume = True

        #Get all indicators and store them in the DataFrame
        if goodVolume:
            #Mark which criteria are met
            #print("DO SOMETHING HERE")
            goldenCross, deathCross = scanStock(df, strategy)

        if((strategy == "up" and goldenCross) or (strategy == "Up" and goldenCross)):
            stockListStr += stock + ","
        elif ((strategy == "down" and deathCross) or (strategy == "Down" and deathCross)):
            stockListStr += stock + ","

        #break out of loop after searching X stocks.
        if(iters >= loopIters or intThread == 1):
            break

    return stockListStr

#testList = si.tickers_sp500()
#testList = ["QCOM", "PEP", "SVB", "TSLA"]
#print(len(testList))
# = checkStocks("dow", "d", "down", "", "", 550)
#print (finalList2)
