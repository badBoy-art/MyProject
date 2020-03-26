# coding=utf-8
dict = {'name':'zhangsan','Age':7,'class':'first'}

print dict['Age']

dict['Age'] = 8

dict['school'] = 'DPS School'

print dict['Age']
print dict['school']
print dict.get('ss','aa')
print dict.keys()
print dict.items()