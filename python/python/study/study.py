import pandas as pd

train = pd.read_csv(r'/Users/xuedui.zhao/Desktop/CBD_Location/test.csv')
train['time_stamp'] = pd.to_datetime(pd.Series(train['time_stamp']))

train['Year'] = train['time_stamp'].apply(lambda x: x.year)
train['Month'] = train['time_stamp'].apply(lambda x: x.month)
train['weekday'] = train['time_stamp'].dt.dayofweek
train['time'] = train['time_stamp'].dt.time

class DataFrameImputer(TransformerMixin):
    def fit(self, X, y=None):
        for c in X:
            if X[c].dtype == np.dtype('O'):
                fill_number = X[c].value_counts().index[0]
                self.fill = pd.Series(fill_number, index=X.columns)
            else:
                fill_number = X[c].median()
                self.fill = pd.Series(fill_number, index=X.columns)
        return self

        def transform(self, X, y=None):
            return X.fillna(self.fill)

train = DataFrameImputer().fit_transform(train)

print(train.info())